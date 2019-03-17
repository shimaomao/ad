package com.planx.advertise.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.advertise.dto.UserDTO;
import com.planx.advertise.model.User;
import com.planx.advertise.model.UserToken;
import com.planx.advertise.repository.UserRepository;
import com.planx.advertise.repository.UserTokenRepository;
import com.planx.advertise.system.config.SecurityUserDetailsService;
import com.planx.advertise.system.exception.ApplicationException;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.system.mail.MailService;

@Service
public class UserServiceImpl implements UserService {

	private static final long TEN_MINUTES = 10 * 60 * 1000L;

	private static final long TWENTY_FOUR_HOURS = 24 * 60 * 60 * 1000L;

	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	@Value("${server.website.url}")
	private String websiteUrl;
	
	@Value("${server.website.name}")
	private String websiteName;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private MailService mailService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User create(UserDTO userDTO) {
		User user = userDTO.convert(null);
		User existsUser = findByUsername(user.getUsername());
		if (existsUser != null) {
			if (existsUser.getState() == User.STATE_USER_INIT) {
				user.setId(existsUser.getId());
			} else {
				throw new ApplicationException("username has been used");
			}
		}

		String hash = encoder.encode(user.getPassword());
		user.setPassword(hash);
		user.setState(User.STATE_USER_INIT);
		return userRepository.save(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePasswordByToken(String token, String newPassword) {
		long now = System.currentTimeMillis();
		UserToken userToken = userTokenRepository.findByToken(token)
				.orElseThrow(() -> new ApplicationException("token invalid"));
		if (UserToken.TYPE_RESET_PASSWORD != userToken.getType()) {
			throw new ApplicationException("token invalid");
		}
		if (now > userToken.getExpireTime() || UserToken.STATE_NOT_USED != userToken.getState()) {
			throw new ApplicationException("token expired");
		}
		User user = this.findById(userToken.getUserId());
		if (null == user) {
			throw new ResourceNotFoundException("user not found");
		}
		if (User.STATE_USER_INIT == user.getState()) {
			user.setState(User.STATE_USER_ENABLE);
		}
		userToken.setState(UserToken.STATE_USED);
		userTokenRepository.save(userToken);

		String hash = encoder.encode(newPassword);
		user.setPassword(hash);
		userRepository.save(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePassword(String oldPassword, String newPassword) {
		User user = securityUserDetailsService.currentUser();
		if (null == user) {
			throw new ApplicationException("Access denied");
		}
		if (!encoder.matches(oldPassword, user.getPassword())) {
			throw new ApplicationException("Password not correct");
		}
		String hash = encoder.encode(newPassword);
		user.setPassword(hash);
		userRepository.save(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User disableUser(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
		user.setState(User.STATE_USER_DISABLE);
		return userRepository.save(user);
	}
	
	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public User findById(String id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void sendRegisterMail(String username) {
		long now = System.currentTimeMillis();
		User user = this.findByUsername(username);
		if (user == null) {
			throw new ResourceNotFoundException("User not found.");
		}
		if (User.STATE_USER_INIT != user.getState()) {
			throw new ApplicationException("account has been verified");
		}
		List<UserToken> tokens = userTokenRepository.findByUserIdAndType(user.getId(), UserToken.TYPE_REGISTER);
		tokens.stream().max((o1, o2) -> {
			return Long.compare(o1.getExpireTime(), o2.getExpireTime());
		}).ifPresent(token -> {
			if (now < token.getCreateTime() + TEN_MINUTES) {
				throw new ApplicationException(
						"A verification email has been sent and cannot be re-sent within 10 mins.");
			}
		});
		UserToken userToken = this.genToken(user);
		userToken.setType(UserToken.TYPE_REGISTER);
		userTokenRepository.save(userToken);
		Map<String, Object> params = new HashMap<>();
		params.put("token", userToken.getToken());
		params.put("websiteUrl", websiteUrl);
		params.put("websiteName", websiteName);
		mailService.sendTemplateMail("mail/register", user.getUsername(), websiteName + " Verify Email", params);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User accountActive(String token) {
		long now = System.currentTimeMillis();
		UserToken userToken = userTokenRepository.findByToken(token)
				.orElseThrow(() -> new ApplicationException("token invalid"));
		if (UserToken.TYPE_REGISTER != userToken.getType()) {
			throw new ApplicationException("token invalid");
		}
		if (now > userToken.getExpireTime() || UserToken.STATE_NOT_USED != userToken.getState()) {
			throw new ApplicationException("token expired");
		}
		User user = this.findById(userToken.getUserId());
		if (null == user) {
			throw new ResourceNotFoundException("user not found");
		}
		if (User.STATE_USER_INIT != user.getState()) {
			throw new ApplicationException("account has been verified");
		}
		userToken.setState(UserToken.STATE_USED);
		userTokenRepository.save(userToken);
		user.setState(User.STATE_USER_ENABLE);
		return userRepository.save(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void sendPasswordMail(String username) {
		long now = System.currentTimeMillis();
		User user = this.findByUsername(username);
		if (user == null) {
			throw new ResourceNotFoundException("User not found.");
		}
		List<UserToken> tokens = userTokenRepository.findByUserIdAndType(user.getId(), UserToken.TYPE_RESET_PASSWORD);
		tokens.stream().max((o1, o2) -> {
			return Long.compare(o1.getExpireTime(), o2.getExpireTime());
		}).ifPresent(token -> {
			if (now < token.getCreateTime() + TEN_MINUTES) {
				throw new ApplicationException(
						"A reset password email has been sent and cannot be re-sent within 10 mins.");
			}
		});
		UserToken userToken = this.genToken(user);
		userToken.setType(UserToken.TYPE_RESET_PASSWORD);
		userTokenRepository.save(userToken);
		Map<String, Object> params = new HashMap<>();
		params.put("token", userToken.getToken());
		params.put("websiteUrl", websiteUrl);
		params.put("websiteName", websiteName);
		mailService.sendTemplateMail("mail/resetPassword", user.getUsername(), websiteName + " Reset Password", params);
	}
	
	private UserToken genToken(User user) {
		long now = System.currentTimeMillis();
		UserToken userToken = new UserToken();
		userToken.setUserId(user.getId());
		userToken.setExpireTime(now + TWENTY_FOUR_HOURS);
		userToken.setState(UserToken.STATE_NOT_USED);
		userToken.setToken(genUuidToken());
		return userTokenRepository.save(userToken);
	}
	
	private String genUuidToken() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}

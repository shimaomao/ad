package com.planx.advertise.system.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.planx.advertise.model.User;
import com.planx.advertise.service.UserService;

@Component
public class SecurityUserDetailsServiceImpl implements UserDetailsService, SecurityUserDetailsService {

	@Autowired
	private UserService uservice;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = uservice.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return SecurityUser.of(user);
	}
	
	@Override
	public User currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		} else {
			return Optional.ofNullable(authentication.getPrincipal())
					.map(securityUser -> ((SecurityUser)securityUser).getUser())
					.orElse(null);
		}		
	}

}

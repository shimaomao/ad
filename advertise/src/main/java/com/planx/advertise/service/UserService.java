package com.planx.advertise.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.planx.advertise.dto.UserDTO;
import com.planx.advertise.model.User;

public interface UserService {
	
	User create(UserDTO userDTO);
	
	void updatePasswordByToken(String token, String newPassword);
	
	void updatePassword(String oldPassword, String newPassword);
	
	User disableUser(String id);
	
	Page<User> findAll(Pageable pageable);

	User findByUsername(String username);

	User findById(String id);

	void sendRegisterMail(String username);

	boolean existsByUsername(String username);

	User accountActive(String token);

	void sendPasswordMail(String username);
	
}

package com.planx.advertise.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planx.advertise.dto.UserDTO;
import com.planx.advertise.model.User;
import com.planx.advertise.response.RestStatus;
import com.planx.advertise.response.RestResponseBody;
import com.planx.advertise.service.UserService;
import com.planx.advertise.system.config.SecurityUserDetailsService;
import com.planx.advertise.system.exception.ResourceNotFoundException;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityUserDetailsService scurityUserService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public RestResponseBody<?> signup(@Valid UserDTO userDTO) {
		User result = userService.create(userDTO);
		userService.sendRegisterMail(result.getUsername());
		return new RestResponseBody<>(RestStatus.SUCCESS);
	}
	
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> findById(@PathVariable String id) {
		User result = userService.findById(id);
		if (null == result) {
			throw new ResourceNotFoundException("user not found");
		}
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}
	
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public RestResponseBody<?> me() {
		User result = scurityUserService.currentUser();
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}
	
	@RequestMapping(value = "/signup/active", method = RequestMethod.POST)
	public RestResponseBody<?> accountActive(@RequestParam String token) {
		userService.accountActive(token);
		return new RestResponseBody<>(RestStatus.SUCCESS);
	}
	
	@RequestMapping(value = "/disable/{userId}", method = RequestMethod.POST)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> disableUser(@PathVariable String userId) {
		userService.disableUser(userId);
		return new RestResponseBody<>(RestStatus.SUCCESS);
	}
	
}

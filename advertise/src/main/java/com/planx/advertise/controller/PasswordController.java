package com.planx.advertise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planx.advertise.response.RestStatus;
import com.planx.advertise.response.RestResponseBody;
import com.planx.advertise.service.UserService;

@RestController
@RequestMapping(path = "/api/password")
public class PasswordController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@PreAuthorize(value = "isAuthenticated()")
	public RestResponseBody<?> updatePassword(@RequestParam String oldPassword, @RequestParam String password) {
		userService.updatePassword(oldPassword, password);
		return new RestResponseBody<>(RestStatus.SUCCESS);
	}
	
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public RestResponseBody<?> sendPasswordMail(@RequestParam String username) {
		userService.sendPasswordMail(username);
		return new RestResponseBody<>(RestStatus.SUCCESS);		
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public RestResponseBody<?> updatePasswordByToken(@RequestParam String token, @RequestParam String password) {
		userService.updatePasswordByToken(token, password);
		return new RestResponseBody<>(RestStatus.SUCCESS);
	}
	
}

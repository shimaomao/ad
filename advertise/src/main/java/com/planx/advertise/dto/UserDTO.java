package com.planx.advertise.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import com.planx.advertise.model.User;

public class UserDTO {

	@NotBlank(message = "Username cannot be empty.")
	@Length(max = 45, message = "Username too long.")
	private String username;
	
	@NotBlank(message = "Password cannot be empty.")
	@Length(min = 6, message = "Password too short.")
	private String password;
	
	private String phone;

	public User convert(User existsUser) {
		User user = new User();
		if (null != existsUser) {
			BeanUtils.copyProperties(existsUser, user);
		}
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		return user;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}

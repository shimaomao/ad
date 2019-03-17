package com.planx.advertise.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final int STATE_USER_INIT = 0;

	public static final int STATE_USER_ENABLE = 1;

	public static final int STATE_USER_DISABLE = -1;

	private static final String ADMIN_NAME = "admin";

	@NotBlank(message = "username cannot be empty")
	private String username;

	@JsonIgnore
	@NotBlank(message = "password cannot be empty")
	private String password;

	private String phone;
	
	private Integer state;

	@ManyToMany
	@JoinTable(name = "ref_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>();

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public Integer getState() {
		return state;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAdmin() {
		return this.getRoles().stream().filter(role -> {
			return ADMIN_NAME.equals(role.getRoleName());
		}).findAny().isPresent();
	}

}

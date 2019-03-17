package com.planx.advertise.system.config;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.planx.advertise.model.User;

public class SecurityUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private static final String ROLE_PREFIX = "ROLE_";

	private User user;
	
	public static SecurityUser of(User user) {
		SecurityUser securityUser = new SecurityUser();
		securityUser.setUser(user);
		user.getRoles().size();
		return securityUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return User.STATE_USER_ENABLE == Optional.ofNullable(user.getState()).orElse(0);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

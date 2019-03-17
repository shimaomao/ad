package com.planx.advertise.system.config;

import com.planx.advertise.model.User;

public interface SecurityUserDetailsService {

	User currentUser();
	
}

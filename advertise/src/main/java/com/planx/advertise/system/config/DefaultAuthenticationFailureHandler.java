package com.planx.advertise.system.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	private String defaultFailureUrl = "/login?error=";
		
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String failureUrl = defaultFailureUrl;
		if (exception instanceof BadCredentialsException) {
			failureUrl += "password";
		} else if (exception instanceof DisabledException) {
			failureUrl += "active";
		} else {
			log.error("onAuthenticationFailure", exception);
		}
		redirectStrategy.sendRedirect(request, response, failureUrl);
	}

}

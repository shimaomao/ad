package com.planx.advertise.page;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.planx.advertise.service.UserService;
import com.planx.advertise.system.exception.ApplicationException;

@Controller
public class AuthenticationPage extends BasePage {

	private static final String ERROR = "ERROR";
	
	private static final String SUCCESS = "SUCCESS";
	
	private static final String FAILED = "FAILED";
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(required = false) String error, Map<String, Object> model) {
		if ("password".equals(error)) {
			model.put("message", "Incorrect username or password");
		} else if ("active".equals(error)) {
			model.put("message", "User not active");
		}
		return "authentication/login";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		return "authentication/signup";
	}
	
	@RequestMapping(value = "/signup/emailsent", method = RequestMethod.GET)
	public String emailSent() {
		return "authentication/signupEmail";
	}
	
	@RequestMapping(value = "/signup/active/{token}", method = RequestMethod.GET)
	public String active(@PathVariable String token, Map<String, Object> model) {
		try {
			userService.accountActive(token);
			model.put("status", SUCCESS);
		} catch (ApplicationException e) {
			model.put("status", FAILED);
			model.put("message", e.getMessage());
		} catch (Exception e) {
			log.error("active error", e);
			model.put("status", ERROR);
		}
		return "authentication/active";
	}
	
}

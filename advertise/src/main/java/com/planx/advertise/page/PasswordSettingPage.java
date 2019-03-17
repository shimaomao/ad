package com.planx.advertise.page;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/setting/password")
public class PasswordSettingPage extends BasePage {

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String updatePassword() {
		return "setting/password/updatePassword";	
	}
	
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String updatePasswordSuccess() {
		return "setting/password/passwordSuccess";	
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String forgotPassword() {
		return "setting/password/forgotPassword";
	}
	
	@RequestMapping(value = "/emailsent", method = RequestMethod.GET)
	public String emailSent() {
		return "setting/password/resetEmail";
	}
	
	@RequestMapping(value = "/reset/{token}", method = RequestMethod.GET)
	public String resetPassword(@PathVariable String token, Map<String, Object> model) {
		model.put("token", token);
		return "setting/password/reset";
	}
	
}

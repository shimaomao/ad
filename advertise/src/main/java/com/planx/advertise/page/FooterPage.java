package com.planx.advertise.page;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FooterPage extends BasePage {

	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public String terms(Map<String, Object> model) {
		return "footer/terms";
	}
	
	@RequestMapping(value = "/privacy", method = RequestMethod.GET)
	public String privacy(Map<String, Object> model) {
		return "footer/privacy";
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact(Map<String, Object> model) {
		return "footer/contact";
	}
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about(Map<String, Object> model) {
		return "footer/about";
	}
		
}

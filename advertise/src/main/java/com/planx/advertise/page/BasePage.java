package com.planx.advertise.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.planx.advertise.model.User;
import com.planx.advertise.system.config.SecurityUserDetailsService;

public class BasePage {

	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	public static final String DEFAULT_TITLE = "Classified Ads Online - community, dating, escorts, jobs, local places...";
	
	public static final String DEFAULT_DESCRIPTION = "local classified ads for automotive, buy/sell/trade, "
			+ "community, dating, escorts, jobs, local places, musician, real estate, rentals, services, "
			+ "and everything else";
	
	public static final String DEFAULT_KEYWORDS = "classified, classified ads, dating, escorts, local places";
			
	@Value("${server.website.name}")
	protected String websiteName;
	
	@Value("${server.website.url}")
	protected String websiteUrl;
	
	@Value("${server.website.domain}")
	protected String websiteDomain;
	
	@ModelAttribute("defaultTitle")
	public String defaultTitle() {
		return DEFAULT_TITLE;
	}
	
	@ModelAttribute("defaultDescription")
	public String defaultDescription() {
		return DEFAULT_DESCRIPTION;
	}
	
	@ModelAttribute("defaultKeywords")
	public String defaultKeywords() {
		return DEFAULT_KEYWORDS;
	}
	
	@ModelAttribute("user")
	public User loadUser() {
		return securityUserDetailsService.currentUser();
	}
	
	@ModelAttribute("websiteName")
	public String websiteName() {
		return websiteName;
	}
	
	@ModelAttribute("websiteUrl")
	public String websiteUrl() {
		return websiteUrl;
	}
	
	@ModelAttribute("websiteDomain")
	public String websiteDomain() {
		return websiteDomain;
	}
	
}

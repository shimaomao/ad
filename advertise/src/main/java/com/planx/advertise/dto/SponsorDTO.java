package com.planx.advertise.dto;

import javax.validation.constraints.Min;

public class SponsorDTO {
	
	public static final Integer ENABLE = 1;
	
	private Integer enableSponsor;
	
	@Min(value = 0)
	private Integer sponsorTerm;

	public Integer getEnableSponsor() {
		return enableSponsor;
	}

	public void setEnableSponsor(Integer enableSponsor) {
		this.enableSponsor = enableSponsor;
	}

	public Integer getSponsorTerm() {
		return sponsorTerm;
	}

	public void setSponsorTerm(Integer sponsorTerm) {
		this.sponsorTerm = sponsorTerm;
	}
	
}

package com.planx.advertise.dto;

import javax.validation.constraints.Min;

public class ToTopDTO {
	
	public static final Integer ENABLE = 1;
	
	private Integer enableToTop;
	
	private Integer toTopFrequency;
	
	@Min(value = 0)
	private Integer toTopTimes;

	public Integer getEnableToTop() {
		return enableToTop;
	}

	public void setEnableToTop(Integer enableToTop) {
		this.enableToTop = enableToTop;
	}

	public Integer getToTopFrequency() {
		return toTopFrequency;
	}

	public void setToTopFrequency(Integer toTopFrequency) {
		this.toTopFrequency = toTopFrequency;
	}

	public Integer getToTopTimes() {
		return toTopTimes;
	}

	public void setToTopTimes(Integer toTopTimes) {
		this.toTopTimes = toTopTimes;
	}
	
}

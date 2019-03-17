package com.planx.advertise.vo;

import java.util.ArrayList;
import java.util.List;

import com.planx.advertise.model.Advertise;

public class DateAdvertise {

	private String refreshTime;
	
	private List<Advertise> advertises = new ArrayList<>();

	public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

	public List<Advertise> getAdvertises() {
		return advertises;
	}

	public void setAdvertises(List<Advertise> advertises) {
		this.advertises = advertises;
	}
	
}

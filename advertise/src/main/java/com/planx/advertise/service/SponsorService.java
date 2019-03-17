package com.planx.advertise.service;

import java.math.BigDecimal;
import java.util.List;

import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.Sponsor;
import com.planx.advertise.model.SponsorItem;

public interface SponsorService {

	SponsorItem create(String consumptionId, String advertiseId, BigDecimal unitPrice, int term);
	
	Sponsor findByAdvertiseId(String advertiseId);
	
	List<Advertise> findSponsorAdvertise(String regionId, String categoryId);

	List<Advertise> findSponsorAdvertise(String regionId);
}

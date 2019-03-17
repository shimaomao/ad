package com.planx.advertise.service;

import com.planx.advertise.dto.SponsorDTO;
import com.planx.advertise.dto.ToTopDTO;
import com.planx.advertise.model.Consumption;

public interface ConsumptionService {

	Consumption create(String advertiseId, ToTopDTO toTopDTO, SponsorDTO sponsorDTO);

}

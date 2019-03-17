package com.planx.advertise.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.advertise.dto.SponsorDTO;
import com.planx.advertise.dto.ToTopDTO;
import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.Consumption;
import com.planx.advertise.model.RegionCategory;
import com.planx.advertise.repository.ConsumptionRepository;
import com.planx.advertise.system.config.SecurityUserDetailsService;
import com.planx.advertise.system.exception.ApplicationException;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {

	@Autowired
	private AdvertiseService advertiseService;
	
	@Autowired
	private ToTopService toTopService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private ConsumptionRepository consumptionRepository;
	
	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Consumption create(String advertiseId, ToTopDTO toTopDTO, SponsorDTO sponsorDTO) {
		
		Advertise advertise = advertiseService.findById(advertiseId);
		
		BigDecimal toTopUnitFee = BigDecimal.ZERO;
		BigDecimal sponsorUnitFee = BigDecimal.ZERO;
		BigDecimal minFee = BigDecimal.ZERO;
		List<RegionCategory> regionCategories = advertiseService.getRegionCategories(advertise);
		for(RegionCategory regionCategory : regionCategories) {
			toTopUnitFee = toTopUnitFee.add(regionCategory.getToTopFee());
			sponsorUnitFee = sponsorUnitFee.add(regionCategory.getSponsorFee());
			if (minFee.compareTo(regionCategory.getMinFee()) < 0) {
				minFee = regionCategory.getMinFee();
			}
		};
		
		BigDecimal total = BigDecimal.ZERO;
		if (toTopDTO.getEnableToTop() == ToTopDTO.ENABLE) {
			total = total.add(toTopUnitFee.multiply(new BigDecimal(toTopDTO.getToTopTimes())));
		}
		if (sponsorDTO.getEnableSponsor() == SponsorDTO.ENABLE) {
			total = total.add(sponsorUnitFee.multiply(new BigDecimal(sponsorDTO.getSponsorTerm())));
		}
		if (minFee.compareTo(total) > 0) {
			throw new ApplicationException("minimum purchase is $" + minFee);
		}
		
		Consumption consumption = null;
		
		if (total.compareTo(BigDecimal.ZERO) > 0) {
			consumption = new Consumption();
			consumption.setUserId(securityUserDetailsService.currentUser().getId());
			consumption.setTotal(total);
			consumption = consumptionRepository.save(consumption);
			
			if (toTopDTO.getEnableToTop() == ToTopDTO.ENABLE) {
				toTopService.create(consumption.getId(), advertise.getId(), toTopUnitFee, toTopDTO.getToTopTimes(), toTopDTO.getToTopFrequency());
			}
			
			if (sponsorDTO.getEnableSponsor() == SponsorDTO.ENABLE) {
				sponsorService.create(consumption.getId(), advertise.getId(), sponsorUnitFee, sponsorDTO.getSponsorTerm());
			}
		}
		
		return consumption;
	}

}

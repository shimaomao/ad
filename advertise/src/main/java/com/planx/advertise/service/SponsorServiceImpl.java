package com.planx.advertise.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.Sponsor;
import com.planx.advertise.model.SponsorItem;
import com.planx.advertise.repository.SponsorItemRepository;
import com.planx.advertise.repository.SponsorRepository;

@Service
public class SponsorServiceImpl implements SponsorService {

	private static final long MS_PER_WEEK = 7 * 24 * 60 * 60 * 1000L;
			
	@Autowired
	private SponsorRepository sponsorRepository;

	@Autowired
	private SponsorItemRepository sponsorItemRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SponsorItem create(String consumptionId, String advertiseId, BigDecimal unitPrice, int term) {
		long now = System.currentTimeMillis();
		Sponsor sponsor = sponsorRepository.findByAdvertiseId(advertiseId).orElse(null);
		if (sponsor == null) {
			sponsor = new Sponsor();
			sponsor.setAdvertiseId(advertiseId);
			sponsor.setTerm(term);
			sponsor.setExpireTime(now + term * MS_PER_WEEK);
		} else {
			sponsor.setTerm(sponsor.getTerm() + term);
			if (sponsor.getExpireTime() < now) {
				sponsor.setExpireTime(now + term * MS_PER_WEEK);
			} else {
				sponsor.setExpireTime(sponsor.getExpireTime() + term * MS_PER_WEEK);
			}
		}
		sponsorRepository.save(sponsor);

		SponsorItem sponsorItem = new SponsorItem();
		sponsorItem.setAdvertiseId(advertiseId);
		sponsorItem.setConsumptionId(consumptionId);
		sponsorItem.setUnitPrice(unitPrice);
		sponsorItem.setTerm(term);

		return sponsorItemRepository.save(sponsorItem);

	}

	@Override
	public Sponsor findByAdvertiseId(String advertiseId) {
		return sponsorRepository.findByAdvertiseId(advertiseId).orElse(null);
	}

	@Override
	public List<Advertise> findSponsorAdvertise(String regionId, String categoryId) {
		long now = System.currentTimeMillis();
		return sponsorRepository.findValidSponsor(regionId, categoryId, now);

	}

	@Override
	public List<Advertise> findSponsorAdvertise(String regionId) {
		long now = System.currentTimeMillis();
		return sponsorRepository.findValidSponsor(regionId, now);

	}
}

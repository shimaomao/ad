package com.planx.advertise.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.ToTop;
import com.planx.advertise.model.ToTopItem;
import com.planx.advertise.repository.ToTopItemRepository;
import com.planx.advertise.repository.ToTopRepository;

@Service
public class ToTopServiceImpl implements ToTopService {

	private static final long MS_PER_HOUR = 60 * 60 * 1000L;
	
	@Autowired
	private AdvertiseService advertiseService;
	
	@Autowired
	private ToTopRepository toTopRepository;
	
	@Autowired
	private ToTopItemRepository toTopItemRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ToTopItem create(String consumptionId, String advertiseId, BigDecimal unitPrice, int times, int frequency) {
		long now = System.currentTimeMillis(); 
		ToTop toTop = toTopRepository.findByAdvertiseId(advertiseId).orElse(null);
		if (toTop == null) {
			toTop = new ToTop();
			toTop.setAdvertiseId(advertiseId);
			toTop.setLeftTimes(times);
			toTop.setTimes(times);
		} else {
			toTop.setLeftTimes(toTop.getLeftTimes() + times);
			toTop.setTimes(toTop.getTimes() + times);
		}
		toTop.setFrequency(frequency);
		toTop.setNextToTopTime(now + frequency * MS_PER_HOUR);
		toTopRepository.save(toTop);
		
		ToTopItem toTopItem = new ToTopItem();
		toTopItem.setAdvertiseId(advertiseId);
		toTopItem.setConsumptionId(consumptionId);
		toTopItem.setUnitPrice(unitPrice);
		toTopItem.setFrequency(frequency);
		toTopItem.setTimes(times);
		
		return toTopItemRepository.save(toTopItem);
		
	}

	@Override
	public Page<ToTop> findAll(Pageable pageable) {
		return toTopRepository.findAll(pageable);
	}

	@Override
	public ToTop findByAdvertiseId(String advertiseId) {
		return toTopRepository.findByAdvertiseId(advertiseId).orElse(null);
	}

	@Override
	public List<ToTop> listValidToTop(Long currentTime) {
		return toTopRepository.listValidToTop(currentTime);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void bumpToTop(ToTop toTop) {
		String advertiseId = toTop.getAdvertiseId();
		Advertise advertise = advertiseService.findById(advertiseId);
		if (advertise != null) {
			advertise.setRefreshTime(toTop.getNextToTopTime());
			advertiseService.save(advertise);
		}
		toTop.setLeftTimes(toTop.getLeftTimes() - 1);
		toTop.setNextToTopTime(toTop.getNextToTopTime() + toTop.getFrequency() * MS_PER_HOUR);
		toTopRepository.save(toTop);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void bumpToTop(List<ToTop> toTops) {
		toTops.stream().forEach(toTop -> {
			bumpToTop(toTop);
		});
	}
	
}

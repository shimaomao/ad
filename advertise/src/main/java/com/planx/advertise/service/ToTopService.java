package com.planx.advertise.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.planx.advertise.model.ToTop;
import com.planx.advertise.model.ToTopItem;

public interface ToTopService {

	ToTopItem create(String consumptionId, String advertiseId, BigDecimal unitPrice, int times, int frequency);

	Page<ToTop> findAll(Pageable pageable);
	
	ToTop findByAdvertiseId(String advertiseId);

	List<ToTop> listValidToTop(Long currentTime);

	void bumpToTop(ToTop toTop);

	void bumpToTop(List<ToTop> toTops);
	
}

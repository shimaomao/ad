package com.planx.advertise.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.planx.advertise.dto.AdvertiseQueryDTO;
import com.planx.advertise.model.Advertise;

public interface AdvertiseCustomerRepository {

	Page<Advertise> findAdvertise(AdvertiseQueryDTO advertiseQueryDTO, Pageable pageable);
	
}

package com.planx.advertise.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.planx.advertise.dto.AdvertiseDTO;
import com.planx.advertise.dto.AdvertiseQueryDTO;
import com.planx.advertise.dto.SponsorDTO;
import com.planx.advertise.dto.ToTopDTO;
import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.RegionCategory;

public interface AdvertiseService {

	Advertise create(String regionCategoryId, AdvertiseDTO advertiseDTO);
	
	Advertise active(String advertiseId, ToTopDTO toTopDTO, SponsorDTO sponsorDTO);
	
	void softDelete(String advertiseId);

	Advertise update(String advertiseId, AdvertiseDTO advertiseDTO);
	
	Advertise save(Advertise advertise);
	
	Page<Advertise> list(String uniqueRegion, String uniqueCategory, Pageable pageable);
	
	Page<Advertise> gallery(String uniqueRegion, String uniqueCategory, Pageable pageable);
	
	Page<Advertise> videos(String uniqueRegion, String uniqueCategory, Pageable pageable);

	Page<Advertise> findAdvertise(AdvertiseQueryDTO advertiseQueryDTO, Pageable pageable);

	Advertise findById(String advertiseId);

	boolean hasAdPermission(Advertise advertise);

	List<RegionCategory> getRegionCategories(Advertise advertise);

}

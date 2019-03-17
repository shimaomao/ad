package com.planx.advertise.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planx.advertise.dto.AdvertiseDTO;
import com.planx.advertise.dto.SponsorDTO;
import com.planx.advertise.dto.ToTopDTO;
import com.planx.advertise.model.Advertise;
import com.planx.advertise.response.RestStatus;
import com.planx.advertise.response.RestResponseBody;
import com.planx.advertise.service.AdvertiseService;

@RestController
@RequestMapping(path = "/api/ad")
public class AdvertiseController {

	@Autowired
	private AdvertiseService advertiseService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@PreAuthorize(value = "isAuthenticated()")
	public RestResponseBody<?> create(@RequestParam String regionCategoryId, @Valid AdvertiseDTO advertiseDTO) {
		Advertise result = advertiseService.create(regionCategoryId, advertiseDTO);
		return new RestResponseBody<>(result, RestStatus.SUCCESS);	
	}
	
	@RequestMapping(value = "/update/{advertiseId}", method = RequestMethod.POST)
	@PreAuthorize(value = "isAuthenticated()")
	public RestResponseBody<?> update(@PathVariable String advertiseId, @Valid AdvertiseDTO advertiseDTO) {
		Advertise result = advertiseService.update(advertiseId, advertiseDTO);
		return new RestResponseBody<>(result, RestStatus.SUCCESS);	
	}
	
	@RequestMapping(value = "/active", method = RequestMethod.POST)
	@PreAuthorize(value = "isAuthenticated()")
	public RestResponseBody<?> active(@RequestParam String advertiseId, @Valid ToTopDTO toTopDTO, @Valid SponsorDTO sponsorDTO) {
		Advertise result = advertiseService.active(advertiseId, toTopDTO, sponsorDTO);
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}
	
	@RequestMapping(value = "/delete/{advertiseId}", method = RequestMethod.POST)
	@PreAuthorize(value = "isAuthenticated()")
	public RestResponseBody<?> delete(@PathVariable String advertiseId) {
		advertiseService.softDelete(advertiseId);
		return new RestResponseBody<>(RestStatus.SUCCESS);
	}
	
}

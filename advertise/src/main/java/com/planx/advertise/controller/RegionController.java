package com.planx.advertise.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.planx.advertise.dto.RegionDTO;
import com.planx.advertise.model.Region;
import com.planx.advertise.response.RestStatus;
import com.planx.advertise.response.RestResponseBody;
import com.planx.advertise.service.RegionService;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.vo.RegionTreeNode;

@RestController
@RequestMapping(path = "/api/region")
public class RegionController {

	@Autowired
	private RegionService regionService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> create(@Valid RegionDTO regionDTO) {
		Region result = regionService.create(regionDTO);
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> delete(@PathVariable String id) {
		regionService.softDelete(id);
		return new RestResponseBody<>(RestStatus.SUCCESS);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> update(@PathVariable String id, @Valid RegionDTO regionDTO) {
		Region result = regionService.update(id, regionDTO);
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}

	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> findById(@PathVariable String id) {
		Region result = regionService.findById(id);
		if (null == result) {
			throw new ResourceNotFoundException("region not found");
		}
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> tree() {
		List<RegionTreeNode> result = regionService.getTree();
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}

}

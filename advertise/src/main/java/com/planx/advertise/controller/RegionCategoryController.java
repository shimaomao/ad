package com.planx.advertise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.planx.advertise.response.RestStatus;
import com.planx.advertise.response.RestResponseBody;
import com.planx.advertise.service.RegionCategoryService;
import com.planx.advertise.vo.RegionCategoryTreeNode;

@RestController
@RequestMapping(path = "/api/regioncategory")
public class RegionCategoryController {

	@Autowired
	private RegionCategoryService regionCategoryService;

	@RequestMapping(value = "/tree/byid/{regionId}", method = RequestMethod.GET)
	public RestResponseBody<?> treeByRegionId(@PathVariable String regionId) {
		List<RegionCategoryTreeNode> result = regionCategoryService.getTreeByRegionId(regionId);
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}
	
	@RequestMapping(value = "/tree/byname/{uniqueRegion}", method = RequestMethod.GET)
	public RestResponseBody<?> treeByUniqueRegion(@PathVariable String uniqueRegion) {
		List<RegionCategoryTreeNode> result = regionCategoryService.getTreeByUniqueRegion(uniqueRegion);
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}
}

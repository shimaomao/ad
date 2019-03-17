package com.planx.advertise.service;

import java.util.List;

import com.planx.advertise.dto.RegionCategoryDTO;
import com.planx.advertise.model.RegionCategory;
import com.planx.advertise.vo.RegionCategoryTreeNode;

public interface RegionCategoryService {

	RegionCategory create(String regionId, String categoryId, RegionCategoryDTO regionCategoryDTO);
	
	List<RegionCategory> create(String regionId, List<String> categoryIds);
	
	void softDelete(String regionCategoryId);
	
	RegionCategory update(String regionCategoryId, RegionCategoryDTO regionCategoryDTO);
	
	List<RegionCategory> listByRegionId(String regionId);
	
	List<RegionCategory> listByCategoryId(String categoryId);

	RegionCategory findById(String regionCategoryId);
	
	RegionCategory findByRegionAndCategory(String uniqueRegion, String uniqueCategory);
	
	RegionCategory findByRegionIdAndCategoryId(String regionId, String categoryId);

	List<RegionCategoryTreeNode> getTreeByRegionId(String regionId);
	
	List<RegionCategoryTreeNode> getTreeByUniqueRegion(String uniqueRegion);

}

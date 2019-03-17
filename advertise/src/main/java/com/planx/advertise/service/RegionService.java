package com.planx.advertise.service;

import java.util.List;

import com.planx.advertise.dto.RegionDTO;
import com.planx.advertise.model.Region;
import com.planx.advertise.vo.RegionTreeNode;

public interface RegionService {

	Region create(RegionDTO regionDTO);

	void softDelete(String regionId);

	Region update(String regionId, RegionDTO regionDTO);

	List<Region> findAll();

	List<Region> findByParentId(String parentId);

	Region findById(String regionId);

	List<RegionTreeNode> getTree();

	Region findByUniqueName(String uniqueName);

}

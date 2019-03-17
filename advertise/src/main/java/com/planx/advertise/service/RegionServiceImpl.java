package com.planx.advertise.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.advertise.dto.RegionDTO;
import com.planx.advertise.model.Region;
import com.planx.advertise.repository.RegionRepository;
import com.planx.advertise.system.exception.ApplicationException;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.vo.RegionTreeNode;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionRepository regionRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Region create(RegionDTO regionDTO) {
		Region region = regionDTO.convert(null);
		regionRepository.findByUniqueName(region.getUniqueName()).ifPresent(existRegion -> {
			throw new ApplicationException("unique name already exists");
		});
		return regionRepository.save(region);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void softDelete(String regionId) {
		Region region = regionRepository.findById(regionId)
				.orElseThrow(() -> new ResourceNotFoundException("region not found"));
		region.setDeleteTime(System.currentTimeMillis());
		regionRepository.save(region);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Region update(String regionId, RegionDTO regionDTO) {
		Region region = regionRepository.findById(regionId)
				.orElseThrow(() -> new ResourceNotFoundException("region not found"));
		if (null != regionDTO.getUniqueName() && !regionDTO.getUniqueName().equals(region.getUniqueName())) {
			regionRepository.findByUniqueName(region.getUniqueName()).ifPresent(existRegion -> {
				throw new ApplicationException("unique name already exists");
			});
		}
		region = regionDTO.convert(region);
		return regionRepository.save(region);
	}

	@Override
	public List<Region> findAll() {
		List<Region> result = regionRepository.findAll();
		return result;
	}

	@Override
	public List<Region> findByParentId(String parentId) {
		List<Region> result = regionRepository.findByParentId(parentId);
		return result;
	}

	@Override
	public Region findById(String regionId) {
		return regionRepository.findById(regionId).orElse(null);
	}

	@Override
	public List<RegionTreeNode> getTree() {
		List<Region> regions = findAll();
		Collections.sort(regions);
		Map<String, RegionTreeNode> treeMap = new HashMap<>();
		List<RegionTreeNode> regionTreeNodes = new ArrayList<>();
		List<RegionTreeNode> result = new ArrayList<>();
		for (Region region : regions) {
			RegionTreeNode treeNode = RegionTreeNode.of(region);
			treeMap.put(treeNode.getRegion().getId(), treeNode);
			regionTreeNodes.add(treeNode);
		}
		for (RegionTreeNode treeNode : regionTreeNodes) {
			if (null == treeNode.getRegion().getParentId()) {
				result.add(treeNode);
			} else {
				Optional.ofNullable(treeMap.get(treeNode.getRegion().getParentId())).map(parent -> parent.getChildren())
						.map(children -> children.add(treeNode));
			}
		}
		return result;
	}

	@Override
	public Region findByUniqueName(String uniqueName) {
		return regionRepository.findByUniqueName(uniqueName).orElse(null);
	}
	
}

package com.planx.advertise.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.advertise.dto.RegionCategoryDTO;
import com.planx.advertise.model.Category;
import com.planx.advertise.model.RegionCategory;
import com.planx.advertise.model.Region;
import com.planx.advertise.repository.RegionCategoryRepository;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.vo.RegionCategoryTreeNode;

@Service
public class RegionCategoryServiceImpl implements RegionCategoryService {

	@Autowired
	private RegionCategoryRepository regionCategoryRepository;

	@Autowired
	private RegionService regionService;

	@Autowired
	private CategoryService categoryService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RegionCategory create(String regionId, String categoryId, RegionCategoryDTO regionCategoryDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<RegionCategory> create(String regionId, List<String> categoryIds) {
		Region region = regionService.findById(regionId);
		if (null == region) {
			throw new ResourceNotFoundException("region not found");
		}
		List<RegionCategory> regionCategories = categoryIds.stream()
				.map(categoryId -> categoryService.findById(categoryId)).map(category -> {
					if (null == category) {
						throw new ResourceNotFoundException("category not found");
					}
					return new RegionCategory(region, category);
				}).collect(Collectors.toList());
		return regionCategoryRepository.saveAll(regionCategories);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void softDelete(String regionCategoryId) {
		RegionCategory regionCategory = regionCategoryRepository.findById(regionCategoryId)
				.orElseThrow(() -> new ResourceNotFoundException("region and category not found"));
		regionCategory.setDeleteTime(System.currentTimeMillis());
		regionCategoryRepository.save(regionCategory);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RegionCategory update(String regionCategoryId, RegionCategoryDTO regionCategoryDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegionCategory> listByRegionId(String regionId) {
		List<RegionCategory> result = regionCategoryRepository.findByRegionId(regionId);
		return result;
	}

	@Override
	public List<RegionCategory> listByCategoryId(String categoryId) {
		List<RegionCategory> result = regionCategoryRepository.findByCategoryId(categoryId);
		return result;
	}
	
	@Override
	public RegionCategory findById(String regionCategoryId) {
		return regionCategoryRepository.findById(regionCategoryId).orElse(null);
	}

	@Override
	public RegionCategory findByRegionAndCategory(String uniqueRegion, String uniqueCategory) {
		return regionCategoryRepository.findByRegionAndCategory(uniqueRegion, uniqueCategory).orElse(null);
	}

	@Override
	public RegionCategory findByRegionIdAndCategoryId(String regionId, String categoryId) {
		return regionCategoryRepository.findByRegionIdAndCategoryId(regionId, categoryId).orElse(null);
	}
	
	@Override
	public List<RegionCategoryTreeNode> getTreeByRegionId(String regionId) {
		List<RegionCategory> regionCategories = regionCategoryRepository.findByRegionId(regionId);
		return genRegionCategoryTree(regionCategories);
	}
	
	@Override
	public List<RegionCategoryTreeNode> getTreeByUniqueRegion(String uniqueRegion) {
		Region region = regionService.findByUniqueName(uniqueRegion);
		if (region == null) {
			throw new ResourceNotFoundException("region not found");
		}
		List<RegionCategory> regionCategories = regionCategoryRepository.findByRegionId(region.getId());
		return genRegionCategoryTree(regionCategories);
	}

	private List<RegionCategoryTreeNode> genRegionCategoryTree(List<RegionCategory> regionCategories) {
		Collections.sort(regionCategories, (o1, o2) -> o1.getCategory().compareTo(o2.getCategory()));
		Map<String, RegionCategoryTreeNode> treeMap = new HashMap<>();
		List<RegionCategoryTreeNode> regionCategoryTreeNodes = new ArrayList<>();
		List<RegionCategoryTreeNode> result = new ArrayList<>();
		for (RegionCategory regionCategory : regionCategories) {
			RegionCategoryTreeNode treeNode = RegionCategoryTreeNode.of(regionCategory);
			Category category = treeNode.getCategory();
			if (null == category) {
				throw new ResourceNotFoundException("category not found, region and category id: " + regionCategory.getId());
			}
			treeMap.put(category.getId(), treeNode);
			regionCategoryTreeNodes.add(treeNode);
		}
		for (RegionCategoryTreeNode treeNode : regionCategoryTreeNodes) {
			Category category = treeNode.getCategory();
			if (null == category.getParentId()) {
				result.add(treeNode);
			} else {
				Optional.ofNullable(treeMap.get(category.getParentId())).map(parent -> parent.getChildren())
						.map(children -> children.add(treeNode));
			}
		}
		return result;
	}
}

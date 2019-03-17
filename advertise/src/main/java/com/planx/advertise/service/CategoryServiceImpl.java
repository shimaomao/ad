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

import com.planx.advertise.dto.CategoryDTO;
import com.planx.advertise.model.Category;
import com.planx.advertise.repository.CategoryRepository;
import com.planx.advertise.system.exception.ApplicationException;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.vo.CategoryTreeNode;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Category create(CategoryDTO categoryDTO) {
		Category category = categoryDTO.convert(null);
		categoryRepository.findByUniqueName(category.getUniqueName()).ifPresent(existCategory -> {
			throw new ApplicationException("unique name already exists");
		});
		return categoryRepository.save(category);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void softDelete(String categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category not found"));
		category.setDeleteTime(System.currentTimeMillis());
		categoryRepository.save(category);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Category update(String categoryId, CategoryDTO categoryDTO) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category not found"));
		if (null != categoryDTO.getUniqueName() && !categoryDTO.getUniqueName().equals(category.getUniqueName())) {
			categoryRepository.findByUniqueName(category.getUniqueName()).ifPresent(existCategory -> {
				throw new ApplicationException("unique name already exists");
			});
		}
		category = categoryDTO.convert(category);
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findAll() {
		List<Category> result = categoryRepository.findAll();
		return result;
	}

	@Override
	public Category findById(String categoryId) {
		return categoryRepository.findById(categoryId).orElse(null);
	}

	@Override
	public List<CategoryTreeNode> getTree() {
		List<Category> categories = findAll();
		Collections.sort(categories);
		Map<String, CategoryTreeNode> treeMap = new HashMap<>();
		List<CategoryTreeNode> categoryTreeNodes = new ArrayList<>();
		List<CategoryTreeNode> result = new ArrayList<>();
		for (Category category : categories) {
			CategoryTreeNode treeNode = CategoryTreeNode.of(category);
			treeMap.put(treeNode.getCategory().getId(), treeNode);
			categoryTreeNodes.add(treeNode);
		}
		for (CategoryTreeNode treeNode : categoryTreeNodes) {
			if (null == treeNode.getCategory().getParentId()) {
				result.add(treeNode);
			} else {
				Optional.ofNullable(treeMap.get(treeNode.getCategory().getParentId())).map(parent -> parent.getChildren())
						.map(children -> children.add(treeNode));
			}
		}
		return result;
	}
	
}

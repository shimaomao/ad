package com.planx.advertise.service;

import java.util.List;

import com.planx.advertise.dto.CategoryDTO;
import com.planx.advertise.model.Category;
import com.planx.advertise.vo.CategoryTreeNode;

public interface CategoryService {

	Category create(CategoryDTO categoryDTO);

	void softDelete(String categoryId);

	Category update(String categoryId, CategoryDTO categoryDTO);

	List<Category> findAll();

	Category findById(String categoryId);

	List<CategoryTreeNode> getTree();

}

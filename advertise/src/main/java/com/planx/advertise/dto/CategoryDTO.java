package com.planx.advertise.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.planx.advertise.model.Category;

public class CategoryDTO {

	private String parentId;

	@NotBlank(message = "category name cannot be empty")
	private String name;

	@NotBlank(message = "category unique name cannot be empty")
	private String uniqueName;

	private String description;

	private Integer sort;

	public Category convert(Category existsCategory) {
		Category category = new Category();
		if (null != existsCategory) {
			BeanUtils.copyProperties(existsCategory, category);
		}
		category.setParentId(parentId);
		category.setName(name);
		category.setUniqueName(uniqueName);
		category.setDescription(description);
		category.setSort(sort);
		return category;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}

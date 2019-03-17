package com.planx.advertise.model;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "category")
public class Category extends BaseEntity implements Comparable<Category> {

	private static final long serialVersionUID = 1L;

	private String parentId;

	@NotBlank(message = "category name cannot be empty")
	private String name;

	@NotBlank(message = "category uique name cannot be empty")
	private String uniqueName;

	private String comment;
	
	private String title;
	
	private String keywords;
	
	private String description;

	private Integer sort;
	
	private Integer ageLimit;

	private Integer state;

	@Override
	public int compareTo(Category o) {
		int thisSort = Optional.ofNullable(this.sort).orElse(0);
		int otherSort = Optional.ofNullable(o.sort).orElse(0);
		
		int result = thisSort - otherSort;
		if (0 == result) {
			result = this.name.compareTo(o.getName());
		}
		return result;
	}

	public Integer getAgeLimit() {
		return ageLimit;
	}

	public String getName() {
		return name;
	}

	public String getParentId() {
		return parentId;
	}

	public String getDescription() {
		return description;
	}

	public Integer getSort() {
		return sort;
	}

	public Integer getState() {
		return state;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setAgeLimit(Integer ageLimit) {
		this.ageLimit = ageLimit;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "Category [parentId=" + parentId + ", name=" + name + ", uniqueName=" + uniqueName + ", comment="
				+ comment + ", title=" + title + ", keywords=" + keywords + ", description=" + description + ", sort="
				+ sort + ", ageLimit=" + ageLimit + ", state=" + state + "]";
	}

}

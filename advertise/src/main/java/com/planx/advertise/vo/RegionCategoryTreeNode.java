package com.planx.advertise.vo;

import java.util.ArrayList;
import java.util.List;

import com.planx.advertise.model.Category;
import com.planx.advertise.model.RegionCategory;
import com.planx.advertise.model.Region;

public class RegionCategoryTreeNode {

	private String id;

	private Category category;
	
	private Region region;
	
	private List<RegionCategoryTreeNode> children = new ArrayList<>();

	public static RegionCategoryTreeNode of(RegionCategory regionCategory) {
		RegionCategoryTreeNode regionCategoryTreeNode = new RegionCategoryTreeNode();
		regionCategoryTreeNode.setId(regionCategory.getId());
		regionCategoryTreeNode.setCategory(regionCategory.getCategory());
		regionCategoryTreeNode.setRegion(regionCategory.getRegion());
		return regionCategoryTreeNode;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public List<RegionCategoryTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<RegionCategoryTreeNode> children) {
		this.children = children;
	}

}

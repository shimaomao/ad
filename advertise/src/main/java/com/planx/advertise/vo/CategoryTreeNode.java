package com.planx.advertise.vo;

import java.util.ArrayList;
import java.util.List;

import com.planx.advertise.model.Category;

public class CategoryTreeNode {

	public static CategoryTreeNode of(Category category) {
		CategoryTreeNode node = new CategoryTreeNode();
		node.setCategory(category);
		return node;
	}

	private Category category;
	
	List<CategoryTreeNode> children = new ArrayList<>();

	public List<CategoryTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryTreeNode> children) {
		this.children = children;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}

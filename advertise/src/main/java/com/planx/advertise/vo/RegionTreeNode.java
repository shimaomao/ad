package com.planx.advertise.vo;

import java.util.ArrayList;
import java.util.List;

import com.planx.advertise.model.Region;

public class RegionTreeNode {
	
	public static RegionTreeNode of(Region region) {
		RegionTreeNode node = new RegionTreeNode();
		node.setRegion(region);
		return node;
	}
	
	private Region region;
	
	List<RegionTreeNode> children = new ArrayList<>();

	public List<RegionTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<RegionTreeNode> children) {
		this.children = children;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
	
}

package com.planx.advertise.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.planx.advertise.model.Region;

public class RegionDTO {

	private String parentId;

	@NotBlank(message = "Region name cannot be empty")
	private String name;

	@NotBlank(message = "Region unique name cannot be empty")
	private String uniqueName;

	private String timeZone;

	private Integer sort;

	public Region convert(Region existsRegion) {
		Region region = new Region();
		if (null != existsRegion) {
			BeanUtils.copyProperties(existsRegion, region);
		}
		region.setParentId(parentId);
		region.setName(name);
		region.setUniqueName(uniqueName);
		region.setTimeZone(timeZone);
		region.setSort(sort);
		return region;
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

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}

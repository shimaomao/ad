package com.planx.advertise.model;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "region")
public class Region extends BaseEntity implements Comparable<Region> {

	private static final long serialVersionUID = 1L;

	private String parentId;

	@NotBlank(message = "region name cannot be empty")
	private String name;

	@NotBlank(message = "region unique name cannot be empty")
	private String uniqueName;

	private String timeZone;

	private Integer sort;

	private Integer state;

	@Override
	public int compareTo(Region o) {
		int thisSort = Optional.ofNullable(this.sort).orElse(0);
		int otherSort = Optional.ofNullable(o.sort).orElse(0);
		
		int result = thisSort - otherSort;
		if (0 == result) {
			result = this.name.compareTo(o.getName());
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public String getParentId() {
		return parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public Integer getState() {
		return state;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	@Override
	public String toString() {
		return "Region [parentId=" + parentId + ", name=" + name + ", uniqueName=" + uniqueName
				+ ", timeZone=" + timeZone + ", sort=" + sort + ", state=" + state + "]";
	}

}

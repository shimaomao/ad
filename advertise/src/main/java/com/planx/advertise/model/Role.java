package com.planx.advertise.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "role name cannot be empty")
	private String roleName;
	
	private String description;
	
	private Integer state;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Role [roleName=" + roleName + ", description=" + description + ", state=" + state + "]";
	}
	
}

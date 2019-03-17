package com.planx.advertise.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "to_top")
public class ToTop extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "advertise cannot be null")
	private String advertiseId;

	private Integer frequency;
	
	private Integer times;
	
	private Integer leftTimes;
	
	private Long nextToTopTime;

	public String getAdvertiseId() {
		return advertiseId;
	}

	public void setAdvertiseId(String advertiseId) {
		this.advertiseId = advertiseId;
	}
	
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getLeftTimes() {
		return leftTimes;
	}

	public void setLeftTimes(Integer leftTimes) {
		this.leftTimes = leftTimes;
	}

	public Long getNextToTopTime() {
		return nextToTopTime;
	}

	public void setNextToTopTime(Long nextToTopTime) {
		this.nextToTopTime = nextToTopTime;
	}

	

}

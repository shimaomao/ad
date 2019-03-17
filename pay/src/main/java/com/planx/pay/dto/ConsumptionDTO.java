package com.planx.pay.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public class ConsumptionDTO {
	
	@NotBlank(message = "id cannot be null")
	private String id;
	
	@NotBlank(message = "user cannot be null")
	private String userId;

	private BigDecimal total;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}


}

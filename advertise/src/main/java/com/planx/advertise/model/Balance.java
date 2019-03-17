package com.planx.advertise.model;

import java.math.BigDecimal;

public class Balance extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String userId;
	
	private BigDecimal balance;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}

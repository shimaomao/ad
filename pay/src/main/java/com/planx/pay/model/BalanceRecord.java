package com.planx.pay.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "balance_record")
public class BalanceRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final int RECHARGE_TYPE_BITCOIN = 0;
	
	public static final int CONSUME_TYPE = -1;
	
	private String serialId;
	
	private Integer type;
	
	private String userId;
	
	private BigDecimal amount;

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}

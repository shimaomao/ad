package com.planx.pay.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "bitcoin_address")
public class BitcoinAddress extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String userId;
	
	private String bitcoinAddress;
	
	private Integer state;

	public String getBitcoinAddress() {
		return bitcoinAddress;
	}

	public void setBitcoinAddress(String bitcoinAddress) {
		this.bitcoinAddress = bitcoinAddress;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}

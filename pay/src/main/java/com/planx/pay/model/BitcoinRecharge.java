package com.planx.pay.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "bitcoin_recharge")
public class BitcoinRecharge extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String transactionId;
	
	private String bitcoinAddress;
	
	private String userId;
	
	private BigDecimal bitcoinAmount;

	private BigDecimal exchangeRate;
	
	private BigDecimal cashAmount;
	
	private Integer state;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

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

	public BigDecimal getBitcoinAmount() {
		return bitcoinAmount;
	}

	public void setBitcoinAmount(BigDecimal bitcoinAmount) {
		this.bitcoinAmount = bitcoinAmount;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}

package com.planx.pay.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "delete_time = 0")
@Table(name = "bitcoin_recharge")
public class BitcoinSent extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String transactionId;
	
	private String sentAddress;
		
	private BigDecimal bitcoinAmount;

	private BigDecimal fee;
		
	private Integer state;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSentAddress() {
		return sentAddress;
	}

	public void setSentAddress(String sentAddress) {
		this.sentAddress = sentAddress;
	}

	public BigDecimal getBitcoinAmount() {
		return bitcoinAmount;
	}

	public void setBitcoinAmount(BigDecimal bitcoinAmount) {
		this.bitcoinAmount = bitcoinAmount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}

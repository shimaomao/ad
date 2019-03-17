package com.planx.pay.service;

import java.math.BigDecimal;

public interface BitcoinRechargeService {

	void bitcoinReceive(String transactionId, String bitcoinAddress, BigDecimal bitcoinAmount);
	
}

package com.planx.pay.service;

import java.math.BigDecimal;

import com.planx.pay.model.Balance;

public interface BalanceService {

	Balance findByUserId(String userId);
	
	void recharge(String userId, String serialId, BigDecimal amount);
	
	void consume(String userId, String serialId, BigDecimal amount);

}

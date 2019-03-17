package com.planx.advertise.service;

import com.planx.advertise.model.Balance;
import com.planx.advertise.model.Consumption;

public interface BalanceService {

	Balance findByUserId(String userId);
	
	void consume(Consumption consumption);
	
}

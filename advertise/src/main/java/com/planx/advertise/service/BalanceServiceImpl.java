package com.planx.advertise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.planx.advertise.model.Balance;
import com.planx.advertise.model.Consumption;

@Service
public class BalanceServiceImpl implements BalanceService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${server.pay.url}")
	private String payUrl;
	
	@Override
	public Balance findByUserId(String userId) {
		Balance balance = restTemplate.getForObject(payUrl + "/api/balance/user/" + userId,
				Balance.class);
		return balance;
	}

	@Override
	public void consume(Consumption consumption) {
		restTemplate.postForEntity(payUrl + "/api/balance/consume", consumption, 
				ResponseEntity.class);
	}

}

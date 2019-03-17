package com.planx.advertise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BitcoinAddressServiceImpl implements BitcoinAddressService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${server.pay.url}")
	private String payUrl;

	@Override
	public String freshUserBitcoinAddress(String userId) {
		String bitcoinAddress = restTemplate.getForObject(payUrl + "/api/bitcoin/address/user/" + userId,
				String.class);
		return bitcoinAddress;

	}

}

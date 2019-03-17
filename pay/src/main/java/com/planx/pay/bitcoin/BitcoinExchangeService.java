package com.planx.pay.bitcoin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BitcoinExchangeService {

	private static final String BLOCKCHAIN_URL = "https://blockchain.info/ticker";
	private static final String ECCHANGE_CURRENCY = "USD";
	private static final String ECCHANGE_TYPE = "sell";

	@Autowired
	private RestTemplate restTemplate;

	public BigDecimal getExchangeRate() {
		Map<?, ?> exchangeRates = restTemplate.getForObject(BLOCKCHAIN_URL, Map.class);
		Double exchangeRate = (Double) Optional.ofNullable(exchangeRates.get(ECCHANGE_CURRENCY)).map(rate -> ((Map<?, ?>)rate).get(ECCHANGE_TYPE)).orElse(null);
		return BigDecimal.valueOf(exchangeRate).setScale(2, RoundingMode.HALF_UP);
	}

	public static void main(String[] args) {
		BitcoinExchangeService bitcoinExchangeService = new BitcoinExchangeService();
		bitcoinExchangeService.restTemplate = new RestTemplate();
		System.out.println(bitcoinExchangeService.getExchangeRate());
	}
	
}

package com.planx.pay.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.planx.pay.bitcoin.BitcoinExchangeService;

@RestController
@RequestMapping(path = "/api/bitcoin/exchange")
public class BitcoinExchangeController {

	@Autowired
	private BitcoinExchangeService bitcoinExchangeService;
	
	@RequestMapping(value = "/usd", method = RequestMethod.GET)
	public ResponseEntity<?> exchangeUsd() {
		BigDecimal exchangeRate = bitcoinExchangeService.getExchangeRate();
		return new ResponseEntity<>(exchangeRate, HttpStatus.OK);
	}
	
}

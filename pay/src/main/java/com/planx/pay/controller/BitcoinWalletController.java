package com.planx.pay.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.planx.pay.bitcoin.BitcoinWalletManager;

@RestController
@RequestMapping(path = "/api/bitcoin/wallet")
public class BitcoinWalletController {

	@Autowired
	private BitcoinWalletManager bitcoinWalletManager;
	
	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	public ResponseEntity<?> balance() {
		BigDecimal result = bitcoinWalletManager.getBalance();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}

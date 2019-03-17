package com.planx.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.planx.pay.model.BitcoinAddress;
import com.planx.pay.service.BitcoinAddressService;

@RestController
@RequestMapping(path = "/api/bitcoin/address")
public class BitcoinAddressController {

	@Autowired
	private BitcoinAddressService bitcoinAddressService;
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> freshBitcoinAddress(@PathVariable String userId) {
		BitcoinAddress bitcoinAddress = bitcoinAddressService.freshUserBitcoinAddress(userId);
		return new ResponseEntity<>(bitcoinAddress.getBitcoinAddress(), HttpStatus.OK);
	}
	
}

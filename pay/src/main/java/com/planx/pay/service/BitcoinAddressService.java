package com.planx.pay.service;

import com.planx.pay.model.BitcoinAddress;

public interface BitcoinAddressService {

	BitcoinAddress freshUserBitcoinAddress(String userId);
	
	BitcoinAddress findByBitcoinAddress(String bitcoinAddress);
	
}

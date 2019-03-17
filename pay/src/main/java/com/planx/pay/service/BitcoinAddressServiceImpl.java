package com.planx.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.pay.bitcoin.BitcoinWalletManager;
import com.planx.pay.model.BitcoinAddress;
import com.planx.pay.repository.BitcoinAddressRepository;

@Service
public class BitcoinAddressServiceImpl implements BitcoinAddressService {

	@Autowired
	private BitcoinAddressRepository bitcoinAddressRepository;
	
	@Autowired
	private BitcoinWalletManager bitcoinWalletManager;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BitcoinAddress freshUserBitcoinAddress(String userId) {
		BitcoinAddress bitcoinAddress = bitcoinAddressRepository.findByUserId(userId).orElse(null);
		if (bitcoinAddress != null) {
			return bitcoinAddress;
		}
		String address = bitcoinWalletManager.freshBitcoinAddress().toString();
		bitcoinAddress = new BitcoinAddress();
		bitcoinAddress.setUserId(userId);
		bitcoinAddress.setBitcoinAddress(address);
		bitcoinAddress = bitcoinAddressRepository.save(bitcoinAddress);
		return bitcoinAddress;
	}

	@Override
	public BitcoinAddress findByBitcoinAddress(String bitcoinAddress) {
		return bitcoinAddressRepository.findByBitcoinAddress(bitcoinAddress).orElse(null);
	}

}

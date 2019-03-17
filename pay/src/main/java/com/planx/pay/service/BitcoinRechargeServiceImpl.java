package com.planx.pay.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.pay.bitcoin.BitcoinExchangeService;
import com.planx.pay.model.BitcoinAddress;
import com.planx.pay.model.BitcoinRecharge;
import com.planx.pay.repository.BitcoinRechargeRepository;

@Service
public class BitcoinRechargeServiceImpl implements BitcoinRechargeService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BitcoinRechargeRepository bitcoinRechargeRepository;
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private BitcoinAddressService bitcoinAddressService;
	
	@Autowired
	private BitcoinExchangeService bitcoinExchangeService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void bitcoinReceive(String transactionId, String bitcoinAddress, BigDecimal bitcoinAmount) {
		boolean exists = bitcoinRechargeRepository.findByTransactionIdAndBitcoinAddress(transactionId, bitcoinAddress).isPresent();
		if (exists) {
			return;
		}
		
		BitcoinRecharge bitcoinRecharge = new BitcoinRecharge();
		bitcoinRecharge.setBitcoinAddress(bitcoinAddress);
		bitcoinRecharge.setBitcoinAmount(bitcoinAmount);
		bitcoinRecharge.setTransactionId(transactionId);
		
		BitcoinAddress userAddress = bitcoinAddressService.findByBitcoinAddress(bitcoinAddress);
		if (userAddress != null) {
			bitcoinRecharge.setUserId(userAddress.getUserId());
		}
		
		BigDecimal exchangeRate = BigDecimal.ZERO;
		try {
			exchangeRate = bitcoinExchangeService.getExchangeRate();
		} catch (Exception e) {
			log.error("getExchangeRate error", e);
		}
		
		bitcoinRecharge.setExchangeRate(exchangeRate);
		BigDecimal cashAmount = exchangeRate.multiply(bitcoinAmount);
		bitcoinRecharge.setCashAmount(cashAmount);
		bitcoinRecharge = bitcoinRechargeRepository.save(bitcoinRecharge);
		
		if (userAddress != null) {
			balanceService.recharge(userAddress.getUserId(), bitcoinRecharge.getId(), cashAmount);
		}
		
		return;
	}

}

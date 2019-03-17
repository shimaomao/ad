package com.planx.pay.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.pay.model.Balance;
import com.planx.pay.model.BalanceRecord;
import com.planx.pay.repository.BalanceRecordRepository;
import com.planx.pay.repository.BalanceRepository;
import com.planx.pay.system.exception.ApplicationException;

@Service
public class BalanceServiceImpl implements BalanceService {
	
	@Autowired
	private BalanceRecordRepository balanceRecordRepository;
	
	@Autowired
	private BalanceRepository balanceRepository;

	@Override
	public Balance findByUserId(String userId) {
		return balanceRepository.findByUserId(userId).orElse(null);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void recharge(String userId, String serialId, BigDecimal amount) {
		balanceRecordRepository.findBySerialId(serialId).ifPresent(recharge -> {
			throw new ApplicationException("serialId repeat");
		});
		BalanceRecord balanceRecord = new BalanceRecord();
		balanceRecord.setSerialId(serialId);
		balanceRecord.setUserId(userId);
		balanceRecord.setAmount(amount);
		balanceRecord.setType(BalanceRecord.RECHARGE_TYPE_BITCOIN);
		balanceRecordRepository.save(balanceRecord);
		
		Balance balance = balanceRepository.findByUserId(userId).orElse(null);
		if (balance == null) {
			balance = new Balance();
			balance.setUserId(userId);
			balance.setBalance(amount);
			balanceRepository.save(balance);
		} else {
			balanceRepository.rechage(userId, amount);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void consume(String userId, String serialId, BigDecimal amount) {
		Balance balance = balanceRepository.findByUserId(userId).orElse(null);
		if (balance == null || amount.compareTo(balance.getBalance()) > 0) {
			throw new ApplicationException("credit not enough");
		}
		balanceRecordRepository.findBySerialId(serialId).ifPresent(recharge -> {
			throw new ApplicationException("serialId repeat");
		});
		BalanceRecord balanceRecord = new BalanceRecord();
		balanceRecord.setSerialId(serialId);
		balanceRecord.setUserId(userId);
		balanceRecord.setAmount(amount);
		balanceRecord.setType(BalanceRecord.CONSUME_TYPE);
		balanceRecordRepository.save(balanceRecord);
		
		int result = balanceRepository.consume(userId, amount);
		if (result == 0) {
			throw new ApplicationException("credit not enough");
		}
	}
	
}

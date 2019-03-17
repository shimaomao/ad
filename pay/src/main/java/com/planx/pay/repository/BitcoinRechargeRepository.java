package com.planx.pay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.pay.model.BitcoinRecharge;

@Repository
public interface BitcoinRechargeRepository extends JpaRepository<BitcoinRecharge, String> {

	Optional<BitcoinRecharge> findByTransactionIdAndBitcoinAddress(String transactionId, String bitcoinAddress);
	
}

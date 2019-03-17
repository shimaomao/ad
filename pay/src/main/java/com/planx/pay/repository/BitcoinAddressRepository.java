package com.planx.pay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.pay.model.BitcoinAddress;

@Repository
public interface BitcoinAddressRepository extends JpaRepository<BitcoinAddress, String> {

	Optional<BitcoinAddress> findByUserId(String userId);
	
	Optional<BitcoinAddress> findByBitcoinAddress(String bitcoinAddress);
	
}

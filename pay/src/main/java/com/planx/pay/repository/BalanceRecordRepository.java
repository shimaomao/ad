package com.planx.pay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.pay.model.BalanceRecord;

@Repository
public interface BalanceRecordRepository extends JpaRepository<BalanceRecord, String> {

	Optional<BalanceRecord> findBySerialId(String serialId);
	
}

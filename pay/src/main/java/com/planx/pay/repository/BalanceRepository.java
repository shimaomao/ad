package com.planx.pay.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.planx.pay.model.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, String> {

	Optional<Balance> findByUserId(String userId);
	
	@Modifying
	@Query(value = "update Balance balance set balance.balance = balance.balance + ?2 "
			+ "where balance.userId = ?1")
	int rechage(String userId, BigDecimal amount);
	
	@Modifying
	@Query(value = "update Balance balance set balance.balance = balance.balance - ?2 "
			+ "where balance.userId = ?1")
	int consume(String userId, BigDecimal amount);
}

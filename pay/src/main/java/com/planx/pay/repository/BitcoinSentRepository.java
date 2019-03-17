package com.planx.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.pay.model.BitcoinSent;

@Repository
public interface BitcoinSentRepository extends JpaRepository<BitcoinSent, String> {

}

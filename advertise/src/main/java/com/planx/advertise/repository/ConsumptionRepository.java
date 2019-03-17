package com.planx.advertise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.Consumption;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, String> {

}

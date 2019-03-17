package com.planx.advertise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.Advertise;

@Repository
public interface AdvertiseRepository extends AdvertiseCustomerRepository, JpaRepository<Advertise, String> {

}

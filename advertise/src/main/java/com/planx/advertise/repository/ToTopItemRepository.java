package com.planx.advertise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.ToTopItem;

@Repository
public interface ToTopItemRepository extends JpaRepository<ToTopItem, String> {

}

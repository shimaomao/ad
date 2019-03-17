package com.planx.advertise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.SystemLog;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, String> {

}

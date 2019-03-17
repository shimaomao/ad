package com.planx.advertise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	
}

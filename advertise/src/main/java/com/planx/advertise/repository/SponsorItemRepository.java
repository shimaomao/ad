package com.planx.advertise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.SponsorItem;

@Repository
public interface SponsorItemRepository extends JpaRepository<SponsorItem, String> {

}

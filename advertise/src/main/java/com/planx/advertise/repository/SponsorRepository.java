package com.planx.advertise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, String> {
	
	Optional<Sponsor> findByAdvertiseId(String advertiseId);
	
	@Query(value = "select advertise from Advertise advertise "
			+ "join advertise.category category "
			+ "join advertise.regions region "
			+ "join Sponsor sponsor on advertise.id = sponsor.advertiseId "
			+ "where sponsor.expireTime > ?3 "
			+ "and region.id = ?1 and category.id = ?2 ")
	List<Advertise> findValidSponsor(String regionId, String categoryId, long expireTime);
	
	@Query(value = "select advertise from Advertise advertise "
			+ "join advertise.category category "
			+ "join advertise.regions region "
			+ "join Sponsor sponsor on advertise.id = sponsor.advertiseId "
			+ "where sponsor.expireTime > ?2 and region.id = ?1 ")
	List<Advertise> findValidSponsor(String regionId, long expireTime);
	
}

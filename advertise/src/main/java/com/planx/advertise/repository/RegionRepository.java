package com.planx.advertise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {

	Optional<Region> findByUniqueName(String uniqueName);

	List<Region> findByParentId(String parentId);

}

package com.planx.advertise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.RegionCategory;

@Repository
public interface RegionCategoryRepository extends JpaRepository<RegionCategory, String> {

	@Query(value = "select regionCategory from RegionCategory regionCategory "
			+ "inner join fetch regionCategory.region region "
			+ "inner join fetch regionCategory.category category "
			+ "where region.id = ?1 ")
	List<RegionCategory> findByRegionId(String regionId);
	
	@Query(value = "select regionCategory from RegionCategory regionCategory "
			+ "inner join fetch regionCategory.region region "
			+ "inner join fetch regionCategory.category category "
			+ "where category.id = ?1 ")
	List<RegionCategory> findByCategoryId(String categoryId);
	
	@Query(value = "select regionCategory from RegionCategory regionCategory "
			+ "inner join fetch regionCategory.region region "
			+ "inner join fetch regionCategory.category category "
			+ "where region.uniqueName = ?1 and category.uniqueName = ?2 ")
	Optional<RegionCategory> findByRegionAndCategory(String uniqueRegion, String uniqueCategory);
	
	@Query(value = "select regionCategory from RegionCategory regionCategory "
			+ "inner join fetch regionCategory.region region "
			+ "inner join fetch regionCategory.category category "
			+ "where region.id = ?1 and category.id = ?2 ")
	Optional<RegionCategory> findByRegionIdAndCategoryId(String regionId, String categoryId);
}

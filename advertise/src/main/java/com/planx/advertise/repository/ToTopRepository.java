package com.planx.advertise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.ToTop;

@Repository
public interface ToTopRepository extends JpaRepository<ToTop, String> {

	Optional<ToTop> findByAdvertiseId(String advertiseId);
	
	@Query(value = "select toTop from ToTop toTop "
			+ "where toTop.leftTimes > 0 and toTop.nextToTopTime <= ?1 ")
	List<ToTop> listValidToTop(Long currentTime);
	
}

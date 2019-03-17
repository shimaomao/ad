package com.planx.advertise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

	Optional<Category> findByUniqueName(String uniqueName);

}

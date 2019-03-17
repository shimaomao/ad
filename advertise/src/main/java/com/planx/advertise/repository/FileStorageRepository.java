package com.planx.advertise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planx.advertise.model.FileStorage;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, String> {

}

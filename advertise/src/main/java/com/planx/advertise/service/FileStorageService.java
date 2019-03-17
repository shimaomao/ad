package com.planx.advertise.service;

import org.springframework.web.multipart.MultipartFile;

import com.planx.advertise.model.FileStorage;

public interface FileStorageService {

	FileStorage saveFile(MultipartFile file, String metadata);

	FileStorage findById(String id);

}

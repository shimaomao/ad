package com.planx.advertise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.planx.advertise.model.FileStorage;
import com.planx.advertise.response.RestStatus;
import com.planx.advertise.response.RestResponseBody;
import com.planx.advertise.service.FileStorageService;
import com.planx.advertise.system.exception.ApplicationException;

@RestController
@RequestMapping(path = "/api/fileStorage")
public class FileStorageController {

	@Autowired
	private FileStorageService fileStorageService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@PreAuthorize(value = "isAuthenticated()")
	public RestResponseBody<?> upload(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "metadata", required = false) String metadata) {
		if (null == file || file.isEmpty()) {
			throw new ApplicationException("file cannot be empty");
		}
		if (!file.getContentType().startsWith("image") && !file.getContentType().startsWith("video")) {
			throw new ApplicationException("file type error");
		}
		FileStorage fileStorage = fileStorageService.saveFile(file, metadata);
		return new RestResponseBody<>(fileStorage, RestStatus.SUCCESS);
	}

}

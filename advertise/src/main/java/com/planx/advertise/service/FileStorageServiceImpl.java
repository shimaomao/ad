package com.planx.advertise.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.planx.advertise.model.FileStorage;
import com.planx.advertise.model.User;
import com.planx.advertise.repository.FileStorageRepository;
import com.planx.advertise.system.config.SecurityUserDetailsService;
import com.planx.advertise.system.exception.ApplicationException;
import com.planx.advertise.util.FilenameUtils;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	@Value("${server.upload.basedir}")
	private String basedir;
	
	@Value("${server.upload.baseuri}")
	private String baseuri;

	@Autowired
	private FileStorageRepository fileStorageRepository;

	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FileStorage saveFile(MultipartFile file, String metadata) {
		String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		File filePath = Paths.get(basedir, String.valueOf(fileName.charAt(0)), String.valueOf(fileName.charAt(1)), 
				String.valueOf(fileName.charAt(2))).toFile();
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File saveFile = new File(filePath, fileName);
		Path basePath = Paths.get(basedir);
		Path relativePath = basePath.relativize(saveFile.toPath());
		String uri = Paths.get(baseuri, relativePath.toString()).toString();
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException | IOException e) {
			throw new ApplicationException("file upload failed", e);
		}
		FileStorage fileStorage = new FileStorage();
		fileStorage.setContentType(file.getContentType());
		fileStorage.setFileName(file.getOriginalFilename());
		fileStorage.setLength(file.getSize());
		fileStorage.setMetadata(metadata);
		fileStorage.setUri(uri.toString());
		fileStorage.setPath(saveFile.getAbsolutePath());
		fileStorage.setState(FileStorage.STATE_NOT_USED);
		User user = securityUserDetailsService.currentUser();
		fileStorage.setUserId(user.getId());
		return fileStorageRepository.save(fileStorage);
	}

	@Override
	public FileStorage findById(String id) {
		return fileStorageRepository.findById(id).orElse(null);
	}
}

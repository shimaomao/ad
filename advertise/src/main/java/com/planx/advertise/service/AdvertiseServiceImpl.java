package com.planx.advertise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planx.advertise.dto.AdvertiseDTO;
import com.planx.advertise.dto.AdvertiseQueryDTO;
import com.planx.advertise.dto.SponsorDTO;
import com.planx.advertise.dto.ToTopDTO;
import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.Balance;
import com.planx.advertise.model.Consumption;
import com.planx.advertise.model.FileStorage;
import com.planx.advertise.model.Region;
import com.planx.advertise.model.RegionCategory;
import com.planx.advertise.model.User;
import com.planx.advertise.repository.AdvertiseRepository;
import com.planx.advertise.system.config.SecurityUserDetailsService;
import com.planx.advertise.system.exception.ApplicationException;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.system.log.OperationLog;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {

	@Autowired
	private AdvertiseRepository advertiseRepository;

	@Autowired
	private ConsumptionService consumptionService;

	@Autowired
	private RegionCategoryService regionCategoryService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;

	@Autowired
	private BalanceService balanceService;

	@Autowired
	private PublishLimitService publishLimitService;

	@Override
	@OperationLog(description = "create advertise")
	@Transactional(rollbackFor = Exception.class)
	public Advertise create(String regionCategoryId, AdvertiseDTO advertiseDTO) {
		Advertise advertise = advertiseDTO.convertToAdvertise(null);
		RegionCategory regionCategory = regionCategoryService.findById(regionCategoryId);
		if (null == regionCategory) {
			throw new ResourceNotFoundException("region and category not found");
		}
		long now = System.currentTimeMillis();
		advertise.setCategory(regionCategory.getCategory());
		advertise.getRegions().add(regionCategory.getRegion());
		if (null != advertiseDTO.getVideoId()) {
			FileStorage video = fileStorageService.findById(advertiseDTO.getVideoId());
			if (null == video || !video.isVideo()) {
				throw new ApplicationException("file type error");
			}
			video.setState(FileStorage.STATE_USED);
			advertise.getMedias().add(video);
			advertise.setHasVideo(true);
		} else {
			advertise.setHasVideo(false);
		}
		List<FileStorage> images = advertiseDTO.getImgIds().stream().map(imgId -> {
			FileStorage img = fileStorageService.findById(imgId);
			if (null == img || !img.isImage()) {
				throw new ApplicationException("file type error");
			}
			img.setState(FileStorage.STATE_USED);
			return img;
		}).collect(Collectors.toList());
		advertise.getMedias().addAll(images);
		if (!images.isEmpty()) {
			advertise.setHasImage(true);
		} else {
			advertise.setHasImage(false);
		}
		User user = securityUserDetailsService.currentUser();
		advertise.setUser(user);
		advertise.setRefreshTime(now);
		advertise.setTimeZone(regionCategory.getRegion().getTimeZone());
		advertise.setState(Advertise.STATE_AD_INIT);
		advertiseRepository.save(advertise);
		return advertise;
	}

	@Override
	@OperationLog(description = "active advertise")
	@Transactional(rollbackFor = Exception.class)
	public Advertise active(String advertiseId, ToTopDTO toTopDTO, SponsorDTO sponsorDTO) {
		Advertise advertise = advertiseRepository.findById(advertiseId)
				.orElseThrow(() -> new ResourceNotFoundException("advertise not found"));
		if (!hasAdPermission(advertise)) {
			throw new ApplicationException("permission denied");
		}
		Consumption consumption = consumptionService.create(advertiseId, toTopDTO, sponsorDTO);
		if (consumption != null) {
			User user = securityUserDetailsService.currentUser();
			Balance balance = balanceService.findByUserId(user.getId());
			if (balance == null || consumption.getTotal().compareTo(balance.getBalance()) > 0) {
				throw new ApplicationException("credit not enough");
			}
			balanceService.consume(consumption);

			long now = System.currentTimeMillis();
			advertise.setRefreshTime(now);
		} else if (!publishLimitService.publishOne(advertise.getUser().getId())) {
			throw new ApplicationException("You posted too much in a short time, please take a break or join our promotional plan to continue");
		}
		advertise.setState(Advertise.STATE_AD_ACTIVE);
		return advertiseRepository.save(advertise);
	}

	@Override
	@OperationLog(description = "delete advertise")
	@Transactional(rollbackFor = Exception.class)
	public void softDelete(String advertiseId) {
		Advertise advertise = advertiseRepository.findById(advertiseId)
				.orElseThrow(() -> new ResourceNotFoundException("advertise not found"));
		if (!hasAdPermission(advertise)) {
			throw new ApplicationException("permission denied");
		}
		advertise.setDeleteTime(System.currentTimeMillis());
		advertiseRepository.save(advertise);
	}

	@Override
	@OperationLog(description = "update advertise")
	@Transactional(rollbackFor = Exception.class)
	public Advertise update(String advertiseId, AdvertiseDTO advertiseDTO) {
		Advertise advertise = advertiseRepository.findById(advertiseId)
				.orElseThrow(() -> new ResourceNotFoundException("advertise not found"));
		if (!hasAdPermission(advertise)) {
			throw new ApplicationException("permission denied");
		}
		advertise = advertiseDTO.convertToAdvertise(advertise);
		advertise.setMedias(new ArrayList<FileStorage>());
		if (null != advertiseDTO.getVideoId()) {
			FileStorage video = fileStorageService.findById(advertiseDTO.getVideoId());
			if (null == video || !video.isVideo()) {
				throw new ApplicationException("file type error");
			}
			advertise.getMedias().add(video);
			advertise.setHasVideo(true);
		} else {
			advertise.setHasVideo(false);
		}
		List<FileStorage> images = advertiseDTO.getImgIds().stream().map(imgId -> {
			FileStorage img = fileStorageService.findById(imgId);
			if (null == img || !img.isImage()) {
				throw new ApplicationException("file type error");
			}
			return img;
		}).collect(Collectors.toList());
		advertise.getMedias().addAll(images);
		if (!images.isEmpty()) {
			advertise.setHasImage(true);
		} else {
			advertise.setHasImage(false);
		}
		return advertiseRepository.save(advertise);
	}

	@Override
	public Advertise save(Advertise advertise) {
		return advertiseRepository.save(advertise);
	}

	@Override
	public Page<Advertise> list(String regionId, String categoryId, Pageable pageable) {
		AdvertiseQueryDTO advertiseQueryDTO = new AdvertiseQueryDTO();
		advertiseQueryDTO.setRegionId(regionId);
		advertiseQueryDTO.setCategoryId(categoryId);
		advertiseQueryDTO.setAdStatus(Advertise.STATE_AD_ACTIVE);
		return advertiseRepository.findAdvertise(advertiseQueryDTO, pageable);
	}

	@Override
	public Page<Advertise> gallery(String regionId, String categoryId, Pageable pageable) {
		AdvertiseQueryDTO advertiseQueryDTO = new AdvertiseQueryDTO();
		advertiseQueryDTO.setRegionId(regionId);
		advertiseQueryDTO.setCategoryId(categoryId);
		advertiseQueryDTO.setAdStatus(Advertise.STATE_AD_ACTIVE);
		advertiseQueryDTO.setHasImage(true);
		return advertiseRepository.findAdvertise(advertiseQueryDTO, pageable);
	}

	@Override
	public Page<Advertise> videos(String regionId, String categoryId, Pageable pageable) {
		AdvertiseQueryDTO advertiseQueryDTO = new AdvertiseQueryDTO();
		advertiseQueryDTO.setRegionId(regionId);
		advertiseQueryDTO.setCategoryId(categoryId);
		advertiseQueryDTO.setAdStatus(Advertise.STATE_AD_ACTIVE);
		advertiseQueryDTO.setHasVideo(true);
		return advertiseRepository.findAdvertise(advertiseQueryDTO, pageable);
	}

	@Override
	public Page<Advertise> findAdvertise(AdvertiseQueryDTO advertiseQueryDTO, Pageable pageable) {
		return advertiseRepository.findAdvertise(advertiseQueryDTO, pageable);
	}

	@Override
	public Advertise findById(String advertiseId) {
		return advertiseRepository.findById(advertiseId).orElse(null);
	}

	@Override
	public List<RegionCategory> getRegionCategories(Advertise advertise) {
		List<RegionCategory> result = new ArrayList<>();
		for (Region region : advertise.getRegions()) {
			RegionCategory regionCategory = regionCategoryService.findByRegionIdAndCategoryId(region.getId(),
					advertise.getCategory().getId());
			result.add(regionCategory);
		}
		return result;

	}

	@Override
	public boolean hasAdPermission(Advertise advertise) {
		User user = securityUserDetailsService.currentUser();
		if (null == user || null == advertise.getUser()) {
			return false;
		}
		if (user.isAdmin()) {
			return true;
		}
		if (user.getId().equals(advertise.getUser().getId())) {
			return true;
		}
		return false;
	}

}

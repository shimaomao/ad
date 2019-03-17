package com.planx.advertise.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.Category;
import com.planx.advertise.model.Region;
import com.planx.advertise.model.RegionCategory;
import com.planx.advertise.model.Sponsor;
import com.planx.advertise.service.AdvertiseService;
import com.planx.advertise.service.RegionCategoryService;
import com.planx.advertise.service.RegionService;
import com.planx.advertise.service.SponsorService;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.util.TimeFormatUtils;
import com.planx.advertise.vo.DateAdvertise;

@Controller
@RequestMapping(path = "/ad")
public class AdvertisePage extends BasePage {

	@Autowired
	private AdvertiseService advertiseService;

	@Autowired
	private RegionCategoryService regionCategoryService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private RegionService regionService;

	@RequestMapping(value = { "/all/list/{uniqueRegion}", "/all/list/{uniqueRegion}/{page}" }, method = RequestMethod.GET)
	public String list(@PathVariable String uniqueRegion, @PathVariable(required = false) Integer page,
			Map<String, Object> model) {
		page = Optional.ofNullable(page).orElse(1);
		if (page < 1) {
			throw new ResourceNotFoundException("Page not found");
		}
		Region region = regionService.findByUniqueName(uniqueRegion);
		if (null == region) {
			throw new ResourceNotFoundException("region not found");
		}
		PageRequest pageable = PageRequest.of(page - 1, 100);

		String regionId = region.getId();
		Page<Advertise> advertisePage = advertiseService.list(regionId, null, pageable);
		Page<DateAdvertise> dateAdvertisePage = groupByDate(advertisePage, region.getTimeZone());

		List<Advertise> sponsorAds = sponsorService.findSponsorAdvertise(regionId);
		Collections.shuffle(sponsorAds);
		
		List<Region> nearbyRegions = new ArrayList<>();
		String parentRegionId = region.getParentId();
		if (parentRegionId != null) {
			Region parentRegion = regionService.findById(parentRegionId);
			if (parentRegion.getParentId() != null) {
				nearbyRegions = regionService.findByParentId(parentRegionId);
			}
		}

		String title = region.getName() + " " + DEFAULT_TITLE;
		String description = region.getName() + " " + DEFAULT_DESCRIPTION;
		String keywords = region.getName() + ", " + DEFAULT_KEYWORDS;
		model.put("title", title);
		model.put("description", description);
		model.put("keywords", keywords);

		model.put("nearbyRegions", nearbyRegions);
		model.put("sponsorAds", sponsorAds);
		model.put("region", region);
		model.put("dateAdvertisePage", dateAdvertisePage);
		return "ad/listAll";
	}

	@RequestMapping(value = { "/list/{uniqueRegion}/{uniqueCategory}",
			"/list/{uniqueRegion}/{uniqueCategory}/{page}" }, method = RequestMethod.GET)
	public String list(@PathVariable String uniqueRegion, @PathVariable String uniqueCategory,
			@PathVariable(required = false) Integer page, Map<String, Object> model) {
		page = Optional.ofNullable(page).orElse(1);
		if (page < 1) {
			throw new ResourceNotFoundException("Page not found");
		}
		RegionCategory regionCategory = regionCategoryService.findByRegionAndCategory(uniqueRegion, uniqueCategory);
		if (null == regionCategory) {
			throw new ResourceNotFoundException("region and category not found");
		}
		PageRequest pageable = PageRequest.of(page - 1, 100);

		String regionId = regionCategory.getRegion().getId();
		String categoryId = regionCategory.getCategory().getId();
		Page<Advertise> advertisePage = advertiseService.list(regionId, categoryId, pageable);
		Page<DateAdvertise> dateAdvertisePage = groupByDate(advertisePage, regionCategory.getRegion().getTimeZone());

		List<Advertise> sponsorAds = sponsorService.findSponsorAdvertise(regionId, categoryId);
		Collections.shuffle(sponsorAds);
		
		List<Region> nearbyRegions = new ArrayList<>();
		String parentRegionId = regionCategory.getRegion().getParentId();
		if (parentRegionId != null) {
			Region parentRegion = regionService.findById(parentRegionId);
			if (parentRegion.getParentId() != null) {
				nearbyRegions = regionService.findByParentId(parentRegionId);
			}
		}

		Category category = regionCategory.getCategory();
		String regionName = regionCategory.getRegion().getName();

		String title = category.getTitle();
		title = title == null ? DEFAULT_TITLE : title.replace("${region}", regionName);
		String description = category.getDescription();
		description = description == null ? DEFAULT_DESCRIPTION : description.replace("${region}", regionName);
		String keywords = category.getKeywords();
		keywords = keywords == null ? DEFAULT_KEYWORDS : keywords.replace("${region}", regionName);
		
		String canonical = "https://" + regionCategory.getRegion().getUniqueName() + "." + websiteDomain;
		model.put("canonical", canonical);
		model.put("title", title);
		model.put("description", description);
		model.put("keywords", keywords);

		model.put("nearbyRegions", nearbyRegions);
		model.put("sponsorAds", sponsorAds);
		model.put("regionCategory", regionCategory);
		model.put("dateAdvertisePage", dateAdvertisePage);
		return "ad/list";
	}

	@RequestMapping(value = { "/all/gallery/{uniqueRegion}", "/all/gallery/{uniqueRegion}/{page}" }, method = RequestMethod.GET)
	public String gallery(@PathVariable String uniqueRegion, @PathVariable(required = false) Integer page,
			Map<String, Object> model) {
		page = Optional.ofNullable(page).orElse(1);
		if (page < 1) {
			throw new ResourceNotFoundException("Page not found");
		}
		Region region = regionService.findByUniqueName(uniqueRegion);
		if (null == region) {
			throw new ResourceNotFoundException("region not found");
		}
		PageRequest pageable = PageRequest.of(page - 1, 24);

		String regionId = region.getId();
		Page<Advertise> advertisePage = advertiseService.gallery(regionId, null, pageable);

		List<Region> nearbyRegions = new ArrayList<>();
		String parentRegionId = region.getParentId();
		if (parentRegionId != null) {
			Region parentRegion = regionService.findById(parentRegionId);
			if (parentRegion.getParentId() != null) {
				nearbyRegions = regionService.findByParentId(parentRegionId);
			}
		}
		
		String title = region.getName() + " " + DEFAULT_TITLE;
		String description = region.getName() + " " + DEFAULT_DESCRIPTION;
		String keywords = region.getName() + ", " + DEFAULT_KEYWORDS;
		model.put("title", title);
		model.put("description", description);
		model.put("keywords", keywords);

		model.put("nearbyRegions", nearbyRegions);
		model.put("region", region);
		model.put("advertisePage", advertisePage);
		return "ad/galleryAll";
	}

	@RequestMapping(value = { "/gallery/{uniqueRegion}/{uniqueCategory}",
			"/gallery/{uniqueRegion}/{uniqueCategory}/{page}" }, method = RequestMethod.GET)
	public String gallery(@PathVariable String uniqueRegion, @PathVariable String uniqueCategory,
			@PathVariable(required = false) Integer page, Map<String, Object> model) {
		page = Optional.ofNullable(page).orElse(1);
		if (page < 1) {
			throw new ResourceNotFoundException("Page not found");
		}
		RegionCategory regionCategory = regionCategoryService.findByRegionAndCategory(uniqueRegion, uniqueCategory);
		if (null == regionCategory) {
			throw new ResourceNotFoundException("region and category not found");
		}
		PageRequest pageable = PageRequest.of(page - 1, 24);

		String regionId = regionCategory.getRegion().getId();
		String categoryId = regionCategory.getCategory().getId();
		Page<Advertise> advertisePage = advertiseService.gallery(regionId, categoryId, pageable);

		List<Region> nearbyRegions = new ArrayList<>();
		String parentRegionId = regionCategory.getRegion().getParentId();
		if (parentRegionId != null) {
			Region parentRegion = regionService.findById(parentRegionId);
			if (parentRegion.getParentId() != null) {
				nearbyRegions = regionService.findByParentId(parentRegionId);
			}
		}
		
		Category category = regionCategory.getCategory();
		String regionName = regionCategory.getRegion().getName();

		String title = category.getTitle();
		title = title == null ? DEFAULT_TITLE : title.replace("${region}", regionName);
		String description = category.getDescription();
		description = description == null ? DEFAULT_DESCRIPTION : description.replace("${region}", regionName);
		String keywords = category.getKeywords();
		keywords = keywords == null ? DEFAULT_KEYWORDS : keywords.replace("${region}", regionName);

		String canonical = "https://" + regionCategory.getRegion().getUniqueName() + "." + websiteDomain;
		model.put("canonical", canonical);
		
		model.put("nearbyRegions", nearbyRegions);
		model.put("regionCategory", regionCategory);
		model.put("advertisePage", advertisePage);
		model.put("title", title);
		model.put("description", description);
		model.put("keywords", keywords);
		return "ad/gallery";
	}

	@RequestMapping(value = "/detail/{advertiseId}", method = RequestMethod.GET)
	public String detail(@PathVariable String advertiseId, Map<String, Object> model) {
		Advertise advertise = advertiseService.findById(advertiseId);
		if (null == advertise) {
			throw new ResourceNotFoundException("advertise not found");
		}
		
		Sponsor sponsor = sponsorService.findByAdvertiseId(advertiseId);
		
		String content = advertise.getContent();
		String escapeContent = null;
		if (sponsor != null && sponsor.getHmlTag() == Sponsor.TYPE_HTML_TAG) {
			escapeContent = content.replace("\r\n", "<br>");
		} else {
			escapeContent = HtmlUtils.htmlEscape(content).replace("\r\n", "<br>");
		}
		advertise.setContent(escapeContent);

		Category category = advertise.getCategory();
		List<Region> regions = advertise.getRegions();
		String regionName = regions.stream().map(region -> region.getName()).collect(Collectors.joining(", "));

		String title = advertise.getTitle();
		if (category.getTitle() != null) {
			title = title + " - " + category.getTitle();
		} else {
			title = title + " - " + DEFAULT_TITLE;
		}
		title = title.replace("${region}", regionName);
		String keywords = category.getKeywords();
		keywords = keywords == null ? DEFAULT_KEYWORDS : keywords.replace("${region}", regionName);

		model.put("advertise", advertise);
		model.put("regions", regions);
		model.put("title", title);
		model.put("description", content);
		model.put("keywords", keywords);
		return "ad/detail";
	}

	@RequestMapping(value = "/detail/{uniqueRegion}/{advertiseId}", method = RequestMethod.GET)
	public String detail(@PathVariable String uniqueRegion, @PathVariable String advertiseId,
			Map<String, Object> model) {
		Advertise advertise = advertiseService.findById(advertiseId);
		if (null == advertise) {
			throw new ResourceNotFoundException("advertise not found");
		}

		Sponsor sponsor = sponsorService.findByAdvertiseId(advertiseId);
		
		String content = advertise.getContent();
		String escapeContent = null;
		if (sponsor != null && sponsor.getHmlTag() == Sponsor.TYPE_HTML_TAG) {
			escapeContent = content.replace("\r\n", "<br>");
		} else {
			escapeContent = HtmlUtils.htmlEscape(content).replace("\r\n", "<br>");
		}
		advertise.setContent(escapeContent);

		Category category = advertise.getCategory();
		Region region = regionService.findByUniqueName(uniqueRegion);
		if (region == null) {
			throw new ResourceNotFoundException("region not found");
		}
		List<Region> regions = new ArrayList<>();
		regions.add(region);

		String title = advertise.getTitle();
		if (category.getTitle() != null) {
			title = title + " - " + category.getTitle();
		} else {
			title = title + " - " + DEFAULT_TITLE;
		}
		title = title.replace("${region}", region.getName());
		String keywords = category.getKeywords();
		keywords = keywords == null ? DEFAULT_KEYWORDS : keywords.replace("${region}", region.getName());

		model.put("advertise", advertise);
		model.put("regions", regions);
		model.put("title", title);
		model.put("description", content);
		model.put("keywords", keywords);
		return "ad/detail";
	}

	private Page<DateAdvertise> groupByDate(Page<Advertise> advertisePage, String timezone) {
		List<DateAdvertise> dateAdvertises = new ArrayList<>();
		String currentDateString = null;
		DateAdvertise dateAdvertise = null;
		for (Advertise advertise : advertisePage.getContent()) {
			String dateString = TimeFormatUtils.format(advertise.getRefreshTime(), timezone, "EEE. MMM. d");
			if (!dateString.equals(currentDateString)) {
				dateAdvertise = new DateAdvertise();
				dateAdvertise.setRefreshTime(dateString);
				dateAdvertises.add(dateAdvertise);
				currentDateString = dateString;
			}
			dateAdvertise.getAdvertises().add(advertise);
		}
		return new PageImpl<DateAdvertise>(dateAdvertises, advertisePage.getPageable(),
				advertisePage.getTotalElements());
	}
}

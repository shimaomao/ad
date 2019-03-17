package com.planx.advertise.page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import com.planx.advertise.dto.AdvertiseQueryDTO;
import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.Balance;
import com.planx.advertise.model.RegionCategory;
import com.planx.advertise.model.Sponsor;
import com.planx.advertise.model.ToTop;
import com.planx.advertise.model.User;
import com.planx.advertise.service.AdvertiseService;
import com.planx.advertise.service.BalanceService;
import com.planx.advertise.service.RegionCategoryService;
import com.planx.advertise.service.RegionService;
import com.planx.advertise.service.SponsorService;
import com.planx.advertise.service.ToTopService;
import com.planx.advertise.system.config.SecurityUserDetailsService;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.vo.RegionCategoryTreeNode;
import com.planx.advertise.vo.RegionTreeNode;

@Controller
@RequestMapping(path = "/setting/ad")
public class AdSettingPage extends BasePage {
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private RegionCategoryService regionCategoryService;
	
	@Autowired
	private AdvertiseService advertiseService;
	
	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private ToTopService toTopService;
	
	@RequestMapping(value = "/region", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String selectRegion(Map<String, Object> model) {
		List<RegionTreeNode> regionTreeNodes = regionService.getTree();
		model.put("regionTreeNodes", regionTreeNodes);
		return "setting/ad/selectRegion";
	}
	
	@RequestMapping(value = "/category/byid/{regionId}", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String selectCategory(@PathVariable String regionId, Map<String, Object> model) {
		List<RegionCategoryTreeNode> regionCategoryTreeNodes = regionCategoryService.getTreeByRegionId(regionId);
		model.put("regionCategoryTreeNodes", regionCategoryTreeNodes);
		return "setting/ad/selectCategoryByRegion";
	}
	
	@RequestMapping(value = "/post/{regionCategoryId}", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String post(@PathVariable String regionCategoryId, Map<String, Object> model) {
		RegionCategory regionCategory = regionCategoryService.findById(regionCategoryId);
		if (null == regionCategory) {
			throw new ResourceNotFoundException("region and category not found");
		}
		model.put("regionCategory", regionCategory);
		return "setting/ad/post";
	}
	
	@RequestMapping(value = "/manage/{advertiseId}", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String manage(@PathVariable String advertiseId, Map<String, Object> model) {
		Advertise advertise = advertiseService.findById(advertiseId);
		if (null == advertise) {
			throw new ResourceNotFoundException("advertise not found");
		}
		String content = advertise.getContent();
		String escapeContent = HtmlUtils.htmlEscape(content).replace("\r\n", "<br>");
		advertise.setContent(escapeContent);
		Sponsor sponsor = sponsorService.findByAdvertiseId(advertiseId);
		ToTop toTop = toTopService.findByAdvertiseId(advertiseId);
		model.put("sponsor", sponsor);
		model.put("toTop", toTop);
		model.put("advertise", advertise);
		return "setting/ad/manage";
	}
	
	@RequestMapping(value = "/renew/{advertiseId}", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String renew(@PathVariable String advertiseId, Map<String, Object> model) {
		Advertise advertise = advertiseService.findById(advertiseId);
		if (null == advertise) {
			throw new ResourceNotFoundException("advertise not found");
		}
		model.put("advertise", advertise);
		return "setting/ad/renew";
	}
	
	@RequestMapping(value = "/update/{advertiseId}", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String update(@PathVariable String advertiseId, Map<String, Object> model) {
		Advertise advertise = advertiseService.findById(advertiseId);
		if (null == advertise) {
			throw new ResourceNotFoundException("advertise not found");
		}
		model.put("advertise", advertise);
		return "setting/ad/update";
	}
	
	@RequestMapping(value = "/preview/{advertiseId}", method = RequestMethod.GET)
	public String preview(@PathVariable String advertiseId, Map<String, Object> model) {
		Advertise advertise = advertiseService.findById(advertiseId);
		if (null == advertise) {
			throw new ResourceNotFoundException("advertise not found");
		}
		String escapeContent = HtmlUtils.htmlEscape(advertise.getContent()).replace("\r\n", "<br>");
		advertise.setContent(escapeContent);
		model.put("advertise", advertise);
		return "setting/ad/preview";
	}
	
	@RequestMapping(value = "/active/{advertiseId}", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String active(@PathVariable String advertiseId, Map<String, Object> model) {
		Advertise advertise = advertiseService.findById(advertiseId);
		if (null == advertise) {
			throw new ResourceNotFoundException("advertise not found");
		}
		BigDecimal toTopUnitFee = BigDecimal.ZERO;
		BigDecimal sponsorUnitFee = BigDecimal.ZERO;
		BigDecimal minFee = BigDecimal.ZERO;
		List<RegionCategory> regionCategories = advertiseService.getRegionCategories(advertise);
		for(RegionCategory regionCategory : regionCategories) {
			toTopUnitFee = toTopUnitFee.add(regionCategory.getToTopFee());
			sponsorUnitFee = sponsorUnitFee.add(regionCategory.getSponsorFee());
			if (minFee.compareTo(regionCategory.getMinFee()) < 0) {
				minFee = regionCategory.getMinFee();
			}
		};
		User user = securityUserDetailsService.currentUser();
		Balance balance = balanceService.findByUserId(user.getId());
		BigDecimal credit = BigDecimal.ZERO;
		if (balance != null) {
			credit = balance.getBalance();
		}
		model.put("toTopUnitFee", toTopUnitFee);
		model.put("sponsorUnitFee", sponsorUnitFee);
		model.put("minFee", minFee);
		model.put("advertise", advertise);
		model.put("credit", credit);
		return "setting/ad/active";
	}

	@RequestMapping(value = "/purchase/{advertiseId}", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String purchase(@PathVariable String advertiseId, Map<String, Object> model) {
		Advertise advertise = advertiseService.findById(advertiseId);
		if (null == advertise) {
			throw new ResourceNotFoundException("advertise not found");
		}
		BigDecimal toTopUnitFee = BigDecimal.ZERO;
		BigDecimal sponsorUnitFee = BigDecimal.ZERO;
		BigDecimal minFee = BigDecimal.ZERO;
		List<RegionCategory> regionCategories = advertiseService.getRegionCategories(advertise);
		for(RegionCategory regionCategory : regionCategories) {
			toTopUnitFee = toTopUnitFee.add(regionCategory.getToTopFee());
			sponsorUnitFee = sponsorUnitFee.add(regionCategory.getSponsorFee());
			if (minFee.compareTo(regionCategory.getMinFee()) < 0) {
				minFee = regionCategory.getMinFee();
			}
		};
		User user = securityUserDetailsService.currentUser();
		Balance balance = balanceService.findByUserId(user.getId());
		BigDecimal credit = BigDecimal.ZERO;
		if (balance != null) {
			credit = balance.getBalance();
		}
		model.put("toTopUnitFee", toTopUnitFee);
		model.put("sponsorUnitFee", sponsorUnitFee);
		model.put("minFee", minFee);
		model.put("advertise", advertise);
		model.put("credit", credit);
		return "setting/ad/purchase";
	}
	
	@RequestMapping(value = "/done/{advertiseId}", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String done(@PathVariable String advertiseId, Map<String, Object> model) {
		Advertise advertise = advertiseService.findById(advertiseId);
		if (null == advertise) {
			throw new ResourceNotFoundException("advertise not found");
		}
		model.put("advertise", advertise);
		return "setting/ad/done";
	}
	
	@RequestMapping(value = {"/list", "/list/{page}"}, method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String list(@PathVariable(required = false) Integer page, Map<String, Object> model) {
		page = Optional.ofNullable(page).orElse(1);
		if (page < 1) {
			throw new ResourceNotFoundException("Page not found");
		}
		PageRequest pageable = PageRequest.of(page - 1, 20);
		User user = securityUserDetailsService.currentUser();
		AdvertiseQueryDTO advertiseQueryDTO = new AdvertiseQueryDTO();
		advertiseQueryDTO.setUserId(user.getId());
		Page<Advertise> advertisePage = advertiseService.findAdvertise(advertiseQueryDTO, pageable);
		Balance balance = balanceService.findByUserId(user.getId());
		BigDecimal credit = BigDecimal.ZERO;
		if (balance != null) {
			credit = balance.getBalance();
		}
		model.put("credit", credit);
		model.put("advertisePage", advertisePage);
		return "setting/ad/list";
	}
	
}

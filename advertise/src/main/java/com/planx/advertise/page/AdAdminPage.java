package com.planx.advertise.page;

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

import com.planx.advertise.dto.AdvertiseQueryDTO;
import com.planx.advertise.model.Advertise;
import com.planx.advertise.service.AdvertiseService;
import com.planx.advertise.system.exception.ResourceNotFoundException;

@Controller
@RequestMapping(path = "/admin/ad")
public class AdAdminPage extends BasePage {

	@Autowired
	private AdvertiseService advertiseService;
	
	@RequestMapping(value = {"/list", "/list/{page}"}, method = RequestMethod.GET)
	@PreAuthorize(value = "hasRole('admin')")
	public String list(AdvertiseQueryDTO advertiseQueryDTO, @PathVariable(required = false) Integer page, Map<String, Object> model) {
		page = Optional.ofNullable(page).orElse(1);
		if (page < 1) {
			throw new ResourceNotFoundException("Page not found");
		}
		PageRequest pageable = PageRequest.of(page - 1, 100);
		Page<Advertise> advertisePage = advertiseService.findAdvertise(advertiseQueryDTO, pageable);
		model.put("advertisePage", advertisePage);
		return "admin/ad/list";
	}
	
}

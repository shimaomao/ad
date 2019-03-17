package com.planx.advertise.page;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.planx.advertise.dto.AdvertiseQueryDTO;
import com.planx.advertise.dto.UserQueryDTO;
import com.planx.advertise.model.Advertise;
import com.planx.advertise.model.Balance;
import com.planx.advertise.model.User;
import com.planx.advertise.service.AdvertiseService;
import com.planx.advertise.service.BalanceService;
import com.planx.advertise.service.UserService;
import com.planx.advertise.system.exception.ResourceNotFoundException;

@Controller
@RequestMapping(path = "/admin/user")
public class UserAdminPage extends BasePage {

	@Autowired
	private UserService userService;

	@Autowired
	private AdvertiseService advertiseService;

	@Autowired
	private BalanceService balanceService;

	@RequestMapping(value = { "/list", "/list/{page}" }, method = RequestMethod.GET)
	@PreAuthorize(value = "hasRole('admin')")
	public String list(UserQueryDTO userQueryDTO, @PathVariable(required = false) Integer page,
			Map<String, Object> model) {
		page = Optional.ofNullable(page).orElse(1);
		if (page < 1) {
			throw new ResourceNotFoundException("Page not found");
		}
		Sort sort = Sort.by(Direction.DESC, "createTime");
		PageRequest pageable = PageRequest.of(page - 1, 100, sort);
		Page<User> userPage = userService.findAll(pageable);
		model.put("userPage", userPage);
		return "admin/user/list";
	}

	@RequestMapping(value = { "/detail/{userId}" }, method = RequestMethod.GET)
	@PreAuthorize(value = "hasRole('admin')")
	public String detail(@PathVariable String userId, @RequestParam(required = false) Integer page,
			Map<String, Object> model) {
		page = Optional.ofNullable(page).orElse(1);
		if (page < 1) {
			throw new ResourceNotFoundException("Page not found");
		}
		PageRequest pageable = PageRequest.of(page - 1, 20);
		User user = userService.findById(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User not found");
		}
		AdvertiseQueryDTO advertiseQueryDTO = new AdvertiseQueryDTO();
		advertiseQueryDTO.setUserId(user.getId());
		Page<Advertise> advertisePage = advertiseService.findAdvertise(advertiseQueryDTO, pageable);
		Balance balance = balanceService.findByUserId(user.getId());
		BigDecimal credit = BigDecimal.ZERO;
		if (balance != null) {
			credit = balance.getBalance();
		}
		model.put("selectedUser", user);
		model.put("credit", credit);
		model.put("advertisePage", advertisePage);
		return "admin/user/detail";
	}

}

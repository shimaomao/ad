package com.planx.advertise.page;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.planx.advertise.model.Balance;
import com.planx.advertise.model.User;
import com.planx.advertise.service.BalanceService;
import com.planx.advertise.service.BitcoinAddressService;
import com.planx.advertise.system.config.SecurityUserDetailsService;

@Controller
@RequestMapping(path = "/setting/credit")
public class CreditPage extends BasePage {

	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	@Autowired
	private BitcoinAddressService bitcoinAddressService;
	
	@Autowired
	private BalanceService balanceService;
	
	@RequestMapping(value = "/bitcoin", method = RequestMethod.GET)
	@PreAuthorize(value = "isAuthenticated()")
	public String privacy(Map<String, Object> model) {
		User user = securityUserDetailsService.currentUser();
		String bitcoinAddress = bitcoinAddressService.freshUserBitcoinAddress(user.getId());
		Balance balance = balanceService.findByUserId(user.getId());
		BigDecimal credit = BigDecimal.ZERO;
		if (balance != null) {
			credit = balance.getBalance();
		}
		model.put("credit", credit);
		model.put("bitcoinAddress", bitcoinAddress);
		return "setting/credit/bitcoin";
	}
	
}

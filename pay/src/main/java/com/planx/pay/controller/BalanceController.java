package com.planx.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.planx.pay.dto.ConsumptionDTO;
import com.planx.pay.model.Balance;
import com.planx.pay.service.BalanceService;

@RestController
@RequestMapping(path = "/api/balance")
public class BalanceController {

	@Autowired
	private BalanceService balanceService;

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getBalance(@PathVariable String userId) {
		Balance balance = balanceService.findByUserId(userId);
		return new ResponseEntity<>(balance, HttpStatus.OK);
	}

	@RequestMapping(value = "/consume", method = RequestMethod.POST)
	public ResponseEntity<?> consume(@RequestBody ConsumptionDTO consumptionDTO) {
		balanceService.consume(consumptionDTO.getUserId(), consumptionDTO.getId(),
				consumptionDTO.getTotal());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}

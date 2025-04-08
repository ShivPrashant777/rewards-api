package com.rewardsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rewardsapi.entity.StatementRecord;
import com.rewardsapi.service.RewardsService;

/**
 * 
 * Controller class for rewards related end points
 */
@RestController
public class RewardsController {
	@Autowired
	private RewardsService rewardsService;

	/**
	 * 
	 * @param customerId: id of the customer
	 * @description Returns the total number of reward points of a given customer.
	 */
	@GetMapping("/get-total-reward-points/{customerId}")
	public int getTotalRewardPoints(@PathVariable int customerId) {
		return rewardsService.getTotalRewardPointsByCustomerId(customerId);
	}

	/**
	 * 
	 * @param customerId: id of the customer
	 * @description Returns the complete statement of a given customer with
	 *              month-wise spends and reward points received.
	 */
	@GetMapping("/get-reward-points-per-month/{customerId}")
	public ResponseEntity<StatementRecord> getRewardPointsPerMonth(@PathVariable int customerId) {
		return new ResponseEntity<>(rewardsService.getRewardPointsPerMonth(customerId), HttpStatus.OK);
	}
}

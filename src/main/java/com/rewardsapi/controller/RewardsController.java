package com.rewardsapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rewardsapi.entity.StatementRecord;
import com.rewardsapi.service.RewardsService;

@RestController
public class RewardsController {
	@Autowired
	private RewardsService rewardsService;

	@GetMapping("/get-total-reward-points/{customerId}")
	public int getTotalRewardPoints(@PathVariable int customerId) {
		return rewardsService.getRewardPointsByCustomerId(customerId);
	}

	@GetMapping("/get-reward-points-per-month/{customerId}")
	public List<StatementRecord> getRewardPointsPerMonth(@PathVariable int customerId) {
		return rewardsService.getRewardPointsPerMonth(customerId);
	}

	@GetMapping("/get-every-customer-reward-points")
	public Map<String, Integer> getEveryCustomerRewardPoints() {
		return rewardsService.getEveryCustomerRewardPoints();
	}
}

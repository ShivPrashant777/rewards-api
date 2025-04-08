package com.rewardsapi.service;

import java.util.List;
import java.util.Map;

import com.rewardsapi.entity.StatementRecord;

public interface RewardsService {
	public int getRewardPointsByCustomerId(int customerId);

	public List<StatementRecord> getRewardPointsPerMonth(int customerId);

	public Map<String, Integer> getEveryCustomerRewardPoints();
}

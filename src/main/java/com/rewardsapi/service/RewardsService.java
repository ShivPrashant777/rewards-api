package com.rewardsapi.service;

import com.rewardsapi.entity.StatementRecord;

/*
 * Service Interface defining the methods that should be implemented by 
 * the rewards service class
 */
public interface RewardsService {
	public int getTotalRewardPointsByCustomerId(int customerId);

	public StatementRecord get3MonthRewardPointsByCustomerId(int customerId);

	public StatementRecord getRewardPointsPerMonthByCustomerId(int customerId);
}

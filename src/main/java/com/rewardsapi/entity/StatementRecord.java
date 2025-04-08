package com.rewardsapi.entity;

import java.util.List;

/**
 * POJO for a customer's statement containing full details including customer
 * name, total rewards points earned and month-wise summary of spends and reward
 * points
 */
public class StatementRecord {
	private int customerId;
	private String customerName;
	private int totalRewardPoints;
	private List<MonthlySummary> monthlySummary;

	public StatementRecord() {
		super();
	}

	public StatementRecord(int customerId, String customerName, int totalRewardPoints,
			List<MonthlySummary> monthlySummary) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.totalRewardPoints = totalRewardPoints;
		this.monthlySummary = monthlySummary;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getTotalRewardPoints() {
		return totalRewardPoints;
	}

	public void setTotalRewardPoints(int totalRewardPoints) {
		this.totalRewardPoints = totalRewardPoints;
	}

	public List<MonthlySummary> getMonthlySummary() {
		return monthlySummary;
	}

	public void setMonthlySummary(List<MonthlySummary> monthlySummary) {
		this.monthlySummary = monthlySummary;
	}

	@Override
	public String toString() {
		return "StatementRecord [customerId=" + customerId + ", customerName=" + customerName + ", totalRewardPoints="
				+ totalRewardPoints + ", monthlySummary=" + monthlySummary + "]";
	}
}

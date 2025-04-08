package com.rewardsapi.entity;

/**
 * 
 */
public class StatementRecord {
	private int customerId;
	private String customerName;
	private String month;
	private int rewardPoints;

	public StatementRecord() {
		super();
	}

	public StatementRecord(int customerId, String customerName, String month, int rewardPoints) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.month = month;
		this.rewardPoints = rewardPoints;
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	@Override
	public String toString() {
		return "StatementRecord [customerId=" + customerId + ", customerName=" + customerName + ", month=" + month
				+ ", rewardPoints=" + rewardPoints + "]";
	}

}

package com.rewardsapi.entity;

/*
 * POJO for month-wise stats like total amount 
 * spent per month and reward points earned that month
 */
public class MonthlySummary {
	private String month;
	private double totalAmountSpent;
	private int rewardPoints;

	public MonthlySummary() {
		super();
	}

	public MonthlySummary(String month, double totalAmountSpent, int rewardPoints) {
		super();
		this.month = month;
		this.totalAmountSpent = totalAmountSpent;
		this.rewardPoints = rewardPoints;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getTotalAmountSpent() {
		return totalAmountSpent;
	}

	public void setTotalAmountSpent(double totalAmountSpent) {
		this.totalAmountSpent = totalAmountSpent;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	@Override
	public String toString() {
		return "MonthSummary [month=" + month + ", totalAmount=" + totalAmountSpent + ", rewardPoints=" + rewardPoints
				+ "]";
	}
}

package com.rewardsapi.entity;

import java.time.LocalDate;

/**
 * POJO for each customer record containing information about customer name,
 * date of the transaction and the amount spent on that transaction
 */
public class CustomerRecord {
	private int customerId;
	private String customerName;
	private LocalDate transactionDate;
	private double transactionAmount;

	public CustomerRecord() {
		super();
	}

	public CustomerRecord(int customerId, String customerName, LocalDate transactionDate, double transactionAmount) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.transactionDate = transactionDate;
		this.transactionAmount = transactionAmount;
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

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	@Override
	public String toString() {
		return "CustomerRecord [customerId=" + customerId + ", customerName=" + customerName + ", transactionDate="
				+ transactionDate + ", transactionAmount=" + transactionAmount + "]";
	}
}

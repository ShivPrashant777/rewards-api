package com.rewardsapi.service;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardsapi.entity.CustomerRecord;
import com.rewardsapi.entity.MonthlySummary;
import com.rewardsapi.entity.StatementRecord;
import com.rewardsapi.exception.CustomerNotFoundException;
import com.rewardsapi.repository.CustomerRepository;

/*
 * An implementation of RewardsService interface containing the business logic
 * for rewards-api
 */
@Service
public class RewardsServiceImpl implements RewardsService {

	private static final String CUSTOMER_NOT_FOUND = "Customer Records Not Found";

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * 
	 * @param customerId: id of the customer
	 * @description Returns the total number of reward points of a given customer.
	 */
	@Override
	public int getTotalRewardPointsByCustomerId(int customerId) {
		List<CustomerRecord> customerRecordsByCustomerId = customerRepository
				.getCustomerRecordsByCustomerId(customerId);
		if (customerRecordsByCustomerId.isEmpty()) {
			throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND);
		}
		int totalPoints = 0;

		for (CustomerRecord record : customerRecordsByCustomerId) {
			totalPoints += calculateRewardPoints(record.getTransactionAmount());
		}

		return totalPoints;
	}

	/**
	 * 
	 * @param customerId: id of the customer
	 * @description Returns the complete statement of a given customer with
	 *              month-wise spends and reward points received.
	 */
	@Override
	public StatementRecord getRewardPointsPerMonth(int customerId) {
		List<CustomerRecord> customerRecordsByCustomerId = customerRepository
				.getCustomerRecordsByCustomerId(customerId);
		if (customerRecordsByCustomerId.isEmpty()) {
			throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND);
		}
		int totalRewardPoints = 0;
		String customerName = customerRecordsByCustomerId.get(0).getCustomerName();

		// maps month to the customer's records for that month
		Map<String, List<CustomerRecord>> monthToRecords = new HashMap<>();
		List<MonthlySummary> monthlySummary = new ArrayList<>();

		// group records by month
		for (CustomerRecord record : customerRecordsByCustomerId) {
			String month = record.getTransactionDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
			monthToRecords.putIfAbsent(month, new ArrayList<>());
			monthToRecords.get(month).add(record);
		}

		// create summary for each month
		for (String month : monthToRecords.keySet()) {
			double monthlyAmountSpent = 0;
			int monthlyRewardPoints = 0;

			for (CustomerRecord record : monthToRecords.get(month)) {
				monthlyAmountSpent += record.getTransactionAmount();
				int points = calculateRewardPoints(record.getTransactionAmount());
				monthlyRewardPoints += points;
				totalRewardPoints += points;
			}
			MonthlySummary summary = new MonthlySummary(month, monthlyAmountSpent, monthlyRewardPoints);
			monthlySummary.add(summary);
		}

		StatementRecord statement = new StatementRecord(customerId, customerName, totalRewardPoints, monthlySummary);
		return statement;
	}

	/**
	 * 
	 * @param transactionAmount: money in dollars for a given transaction
	 * @description Calculates and Returns reward points for a given transaction
	 *              amount
	 */
	public int calculateRewardPoints(double transactionAmount) {
		int points = 0;

		if (transactionAmount > 100) {
			points += 2 * (int) (transactionAmount - 100);
			points += 50;
		} else if (transactionAmount > 50) {
			points += (int) (transactionAmount - 50);
		}

		return points;
	}
}

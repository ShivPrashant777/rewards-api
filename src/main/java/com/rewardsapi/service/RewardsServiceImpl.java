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
import com.rewardsapi.entity.StatementRecord;
import com.rewardsapi.repository.CustomerRepository;

@Service
public class RewardsServiceImpl implements RewardsService {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public int getRewardPointsByCustomerId(int customerId) {
		List<CustomerRecord> customerRecordsByCustomerId = customerRepository
				.getCustomerRecordsByCustomerId(customerId);
		int totalPoints = 0;

		for (CustomerRecord record : customerRecordsByCustomerId) {
			totalPoints += calculateRewardPoints(record.getTransactionAmount());
		}

		return totalPoints;
	}

	@Override
	public List<StatementRecord> getRewardPointsPerMonth(int customerId) {
		List<CustomerRecord> customerRecordsByCustomerId = customerRepository
				.getCustomerRecordsByCustomerId(customerId);
		Map<String, Integer> monthToPoints = new HashMap<>();
		List<StatementRecord> statements = new ArrayList<>();

		for (CustomerRecord record : customerRecordsByCustomerId) {
			String month = record.getTransactionDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
			int points = calculateRewardPoints(record.getTransactionAmount());
			monthToPoints.put(month, monthToPoints.getOrDefault(month, 0) + points);
		}

		String customerName = customerRecordsByCustomerId.get(0).getCustomerName();
		for (Map.Entry<String, Integer> entry : monthToPoints.entrySet()) {
			StatementRecord statement = new StatementRecord(customerId, customerName, entry.getKey(), entry.getValue());
			statements.add(statement);
		}

		return statements;
	}

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

	@Override
	public Map<String, Integer> getEveryCustomerRewardPoints() {
		List<CustomerRecord> allCustomerRecords = customerRepository.getAllCustomerRecords();
		Map<String, Integer> res = new HashMap<>();

		for (CustomerRecord record : allCustomerRecords) {
			int points = getRewardPointsByCustomerId(record.getCustomerId());
			res.put(record.getCustomerName(), points);
		}

		return res;
	}
}

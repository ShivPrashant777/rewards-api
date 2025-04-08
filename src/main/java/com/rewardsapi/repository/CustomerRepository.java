package com.rewardsapi.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.rewardsapi.entity.CustomerRecord;

@Repository
public class CustomerRepository {

	public List<CustomerRecord> allCustomerRecords;

	CustomerRepository() {
		allCustomerRecords = new ArrayList<>();
		allCustomerRecords.add(new CustomerRecord(101, "Jane Doe", LocalDate.of(2025, 4, 1), 49.99));
		allCustomerRecords.add(new CustomerRecord(101, "Jane Doe", LocalDate.of(2025, 3, 1), 64.99));
		allCustomerRecords.add(new CustomerRecord(101, "Jane Doe", LocalDate.of(2025, 12, 19), 139.99));
		allCustomerRecords.add(new CustomerRecord(102, "John Doe", LocalDate.of(2025, 7, 11), 189.99));
		allCustomerRecords.add(new CustomerRecord(103, "Sam Smith", LocalDate.of(2025, 3, 5), 120));
	}

	public List<CustomerRecord> getCustomerRecordsByCustomerId(int customerId) {
		return allCustomerRecords.stream().filter(c -> c.getCustomerId() == customerId).collect(Collectors.toList());
	}

	public List<CustomerRecord> getAllCustomerRecords() {
		return allCustomerRecords;
	}
}

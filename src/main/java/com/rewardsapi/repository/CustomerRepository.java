package com.rewardsapi.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.rewardsapi.entity.CustomerRecord;

/*
 * An emulation of a database/repository of Customer Records
 * using collections
 */
@Repository
public class CustomerRepository {

	public List<CustomerRecord> allCustomerRecords;

	// add dummy data
	CustomerRepository() {
		allCustomerRecords = new ArrayList<>();
		allCustomerRecords.add(new CustomerRecord(101, "Jane Doe", LocalDate.of(2025, 4, 1), 49.99));
		allCustomerRecords.add(new CustomerRecord(101, "Jane Doe", LocalDate.of(2025, 3, 1), 64.99));
		allCustomerRecords.add(new CustomerRecord(102, "John Doe", LocalDate.of(2024, 7, 11), 189.99));
		allCustomerRecords.add(new CustomerRecord(101, "Jane Doe", LocalDate.of(2024, 12, 19), 139.49));
		allCustomerRecords.add(new CustomerRecord(101, "Jane Doe", LocalDate.of(2025, 2, 15), 529.49));
		allCustomerRecords.add(new CustomerRecord(102, "John Doe", LocalDate.of(2025, 4, 8), 786.99));
		allCustomerRecords.add(new CustomerRecord(103, "Sam Smith", LocalDate.of(2025, 3, 16), 439.49));
		allCustomerRecords.add(new CustomerRecord(102, "John Doe", LocalDate.of(2025, 3, 13), 67.99));
		allCustomerRecords.add(new CustomerRecord(103, "Sam Smith", LocalDate.of(2025, 3, 5), 120.0));
		allCustomerRecords.add(new CustomerRecord(101, "Jane Doe", LocalDate.of(2025, 2, 25), 64.99));
	}

	public List<CustomerRecord> getCustomerRecordsByCustomerId(int customerId) {
		return allCustomerRecords.stream().filter(c -> c.getCustomerId() == customerId).collect(Collectors.toList());
	}

	public List<CustomerRecord> getAllCustomerRecords() {
		return allCustomerRecords;
	}
}

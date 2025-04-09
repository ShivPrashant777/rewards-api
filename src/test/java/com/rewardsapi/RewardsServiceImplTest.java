package com.rewardsapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.rewardsapi.entity.CustomerRecord;
import com.rewardsapi.entity.MonthlySummary;
import com.rewardsapi.entity.StatementRecord;
import com.rewardsapi.exception.CustomerNotFoundException;
import com.rewardsapi.repository.CustomerRepository;
import com.rewardsapi.service.RewardsServiceImpl;

/* 
 * Unit tests for RewardsServiceImpl class
 */
@SpringBootTest
public class RewardsServiceImplTest {
	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private RewardsServiceImpl rewardsService;

	private static List<CustomerRecord> records;

	@BeforeAll
	static void beforeAll() {
		records = new ArrayList<>();
		records.add(new CustomerRecord(101, "John Doe", LocalDate.of(2025, 1, 15), 120.0));
		records.add(new CustomerRecord(101, "John Doe", LocalDate.of(2025, 1, 20), 90.0));
		records.add(new CustomerRecord(101, "John Doe", LocalDate.of(2025, 2, 12), 60.0));
		records.add(new CustomerRecord(101, "John Doe", LocalDate.of(2024, 6, 9), 70.0));
	}

	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
	}

	/*
	 * valid case for when a customer record exists, the function should return the
	 * correct total reward points for that customer
	 */
	@Test
	void testGetTotalRewardPointsByCustomerId_CustomerExists() {
		when(customerRepository.getCustomerRecordsByCustomerId(101)).thenReturn(records);
		int totalPoints = rewardsService.getTotalRewardPointsByCustomerId(101);

		assertEquals(160, totalPoints);
	}

	/*
	 * valid case for when a customer record exists, the function should return the
	 * 3 month statement for the customer with the right values for customer name,
	 * total reward points, total amount spent and reward points for any month
	 */
	@Test
	void testGet3MonthRewardPointsByCustomerId_CustomerExists() {
		when(customerRepository.getCustomerRecordsByCustomerId(101)).thenReturn(records);

		StatementRecord statement = rewardsService.get3MonthRewardPointsByCustomerId(101);

		assertEquals(2, statement.getMonthlySummary().size());
		assertEquals(101, statement.getCustomerId());
		assertEquals("John Doe", statement.getCustomerName());
		assertEquals(140, statement.getTotalRewardPoints());

		List<MonthlySummary> summary = statement.getMonthlySummary();
		assertFalse(summary.isEmpty());
	}

	/*
	 * invalid case for when a customer records don't exist, the function should
	 * throw CustomerNotFoundException
	 */
	@Test
	void testGet3MonthRewardPointsByCustomerId_CustomerRecordsNotFound() {
		when(customerRepository.getCustomerRecordsByCustomerId(404)).thenReturn(Collections.emptyList());

		CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
				() -> rewardsService.get3MonthRewardPointsByCustomerId(404));

		assertEquals("Customer Records Not Found", exception.getMessage());
	}

	/*
	 * invalid case for when a customer records don't exist, the function should
	 * throw CustomerNotFoundException
	 */
	@Test
	void testGetTotalRewardPointsByCustomerId_CustomerRecordsNotFound() {
		when(customerRepository.getCustomerRecordsByCustomerId(203)).thenReturn(Collections.emptyList());

		CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
				() -> rewardsService.getTotalRewardPointsByCustomerId(203));

		assertEquals("Customer Records Not Found", exception.getMessage());
	}

	/*
	 * valid case for when a customer record exists, the function should return the
	 * statement for the customer with the right values for customer name, total
	 * reward points, total amount spent and reward points for any month
	 */
	@Test
	void testGetRewardPointsPerMonth_CustomerExists() {
		when(customerRepository.getCustomerRecordsByCustomerId(101)).thenReturn(records);

		StatementRecord statement = rewardsService.getRewardPointsPerMonthByCustomerId(101);

		assertEquals(3, statement.getMonthlySummary().size());
		assertEquals("John Doe", statement.getCustomerName());
		assertEquals(160, statement.getTotalRewardPoints());

		// get monthly summary of January
		MonthlySummary janSummary = null;
		for (MonthlySummary summary : statement.getMonthlySummary()) {
			if (summary.getMonth().equals(Month.JANUARY)) {
				janSummary = summary;
				break;
			}
		}

		assertEquals(210.0, janSummary.getTotalAmountSpent());
		assertEquals(130, janSummary.getRewardPoints());
	}

	/*
	 * invalid case for when a customer records don't exist, the function should
	 * throw CustomerNotFoundException
	 */
	@Test
	void testGetRewardPointsPerMonth_CustomerRecordsNotFound() {
		when(customerRepository.getCustomerRecordsByCustomerId(203)).thenReturn(Collections.emptyList());
		CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
				() -> rewardsService.getRewardPointsPerMonthByCustomerId(203));
		assertEquals("Customer Records Not Found", exception.getMessage());
	}

	/*
	 * test the helper function business logic for calculation of reward points for
	 * a given amount in dollars
	 */
	@Test
	void testCalculateRewardPoints() {
		assertEquals(0, rewardsService.calculateRewardPoints(50));
		assertEquals(10, rewardsService.calculateRewardPoints(60));
		assertEquals(50, rewardsService.calculateRewardPoints(100));
		assertEquals(90, rewardsService.calculateRewardPoints(120));
	}
}

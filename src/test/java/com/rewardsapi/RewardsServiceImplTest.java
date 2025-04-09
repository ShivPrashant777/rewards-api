package com.rewardsapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
		records.add(new CustomerRecord(101, "John Doe", LocalDate.of(2024, 1, 15), 120.0));
		records.add(new CustomerRecord(101, "John Doe", LocalDate.of(2024, 1, 20), 90.0));
		records.add(new CustomerRecord(101, "John Doe", LocalDate.of(2024, 2, 12), 60.0));
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

		assertEquals(140, totalPoints);
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

		StatementRecord statement = rewardsService.getRewardPointsPerMonth(101);

		assertEquals(2, statement.getMonthlySummary().size());
		assertEquals("John Doe", statement.getCustomerName());
		assertEquals(140, statement.getTotalRewardPoints());

		// get monthly summary of January
		MonthlySummary janSummary = null;
		String janMonthString = Month.JANUARY.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		for (MonthlySummary summary : statement.getMonthlySummary()) {
			if (summary.getMonth().equals(janMonthString)) {
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
				() -> rewardsService.getRewardPointsPerMonth(203));
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

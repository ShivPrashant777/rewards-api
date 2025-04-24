package com.rewardsapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rewardsapi.entity.StatementRecord;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardsControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	/*
	 * valid case for when a customer record exists
	 */
	@Test
	void testGetTotalRewardPointsByCustomerId_CustomerExists() {
		ResponseEntity<Integer> response = restTemplate.getForEntity("/api/get-total-reward-points/101", Integer.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1064, response.getBody());
	}

	/*
	 * invalid case for when a customer records don't exist
	 */
	@Test
	void testGetTotalRewardPointsByCustomerId_CustomerRecordsNotFound() {
		ResponseEntity<Object> response = restTemplate.getForEntity("/api/get-total-reward-points/404", Object.class);
		System.out.println("RESPONSE: " + response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/*
	 * valid case for when a customer record exists
	 */
	@Test
	void testGet3MonthRewardPointsByCustomerId_CustomerExists() {
		ResponseEntity<StatementRecord> response = restTemplate.getForEntity("/api/get-3-month-reward-points/101",
				StatementRecord.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(936, response.getBody().getTotalRewardPoints());
	}

	/*
	 * invalid case for when a customer records don't exist
	 */
	@Test
	void testGet3MonthRewardPointsByCustomerId_CustomerRecordsNotFound() {
		ResponseEntity<Object> response = restTemplate.getForEntity("/api/get-3-month-reward-points/404", Object.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/*
	 * valid case for when a customer record exists
	 */
	@Test
	void testGetRewardPointsPerMonthByCustomerId_CustomerExists() {
		ResponseEntity<StatementRecord> response = restTemplate.getForEntity("/api/get-reward-points-per-month/101",
				StatementRecord.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1064, response.getBody().getTotalRewardPoints());
	}

	/*
	 * invalid case for when a customer records don't exist
	 */
	@Test
	void testGetRewardPointsPerMonthByCustomerId_CustomerRecordsNotFound() {
		ResponseEntity<Object> response = restTemplate.getForEntity("/api/get-reward-points-per-month/404",
				Object.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}

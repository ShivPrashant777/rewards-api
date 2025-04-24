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

	@Test
	void testGetTotalRewardPointsByCustomerId() {
		ResponseEntity<Integer> response = restTemplate.getForEntity("/api/get-total-reward-points/101", Integer.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1064, response.getBody());
	}

	@Test
	void testGet3MonthRewardPointsByCustomerId() {
		ResponseEntity<StatementRecord> response = restTemplate.getForEntity("/api/get-3-month-reward-points/101",
				StatementRecord.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(936, response.getBody().getTotalRewardPoints());
	}

	@Test
	void testGetRewardPointsPerMonthByCustomerId() {
		ResponseEntity<StatementRecord> response = restTemplate.getForEntity("/api/get-reward-points-per-month/101",
				StatementRecord.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1064, response.getBody().getTotalRewardPoints());
	}
}

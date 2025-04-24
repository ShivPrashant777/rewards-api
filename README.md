# Rewards-api

## Overview

For a given dataset of customer records containing transaction details, the project provides api endpoints to calculate and return the reward points earned for each customer. It can generate complete statements or statement for the last 3 months.

## Stack

1. Java 17

2. Spring Boot 3.4.4

3. Spring Boot Test 3.4.4

4. Mockito 5.14.2

5. Java Collections Framework for Emulating Database

## Project Structure

- The project follows the standard guidelines for Rest APIs built using Spring Boot. The API endpoints are described in the `RewardsController` class.

- The business logic is written in `RewardsServiceImpl` class which implements the `RewardsService` interface for loose coupling.

- For the sample dataset `CustomerRepository` class emulates our database/repository using Java collections framework.

- The entities/POJO are - 
  
  - **CustomerRecord** - Stores each customer/transaction record containing information about customer id, customer name, date of the transaction and the amount spent on that transaction.
  
  - **MonthlySummary** - Stores month-wise stats like total amount spent per month and reward points earned that month.
  
  - **StatementRecord** - Stores a customer's statement containing full details including customer name, total rewards points earned and month-wise summary of spends and reward points.

- Exception Handling is performed in the `GlobalExceptionHandler` class which also handles the custom exception `CustomerNotFoundException`.

- Unit Tests are written in `RewardsServiceImplTest` class.

## API Endpoints

1. **GET** `/api/get-total-reward-points/{customerId}`
   
   Returns the total number of reward points of a given customer (Integer).
   
   E.g. /api/get-total-reward-points/101
   
   ```json
   1064
   ```

2. **GET** `/api/get-3-month-reward-points/{customerId}`
   
   Returns the number of reward points of a given customer during the last 3 months.
   
   E.g. /api/get-3-month-reward-points/101
   
   ```json
   {
     "customerId": 101,
     "customerName": "Jane Doe",
     "totalRewardPoints": 936,
     "monthlySummary": [
       {
         "month": "APRIL",
         "totalAmountSpent": 49.99,
         "rewardPoints": 0
       },
       {
         "month": "FEBRUARY",
         "totalAmountSpent": 594.48,
         "rewardPoints": 922
       },
       {
         "month": "MARCH",
         "totalAmountSpent": 64.99,
         "rewardPoints": 14
       }
     ]
   }
   ```

3. **GET** `/api/get-reward-points-per-month/{customerId}`
   
   Returns the complete statement of a given customer with month-wise spends and reward points received.
   
   E.g. /api/get-reward-points-per-month/101
   
   ```json
   {
     "customerId": 101,
     "customerName": "Jane Doe",
     "totalRewardPoints": 1064,
     "monthlySummary": [
       {
         "month": "APRIL",
         "totalAmountSpent": 49.99,
         "rewardPoints": 0
       },
       {
         "month": "FEBRUARY",
         "totalAmountSpent": 594.48,
         "rewardPoints": 922
       },
       {
         "month": "DECEMBER",
         "totalAmountSpent": 139.49,
         "rewardPoints": 128
       },
       {
         "month": "MARCH",
         "totalAmountSpent": 64.99,
         "rewardPoints": 14
       }
     ]
   }
   ```

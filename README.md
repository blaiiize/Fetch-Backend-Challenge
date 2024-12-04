# Fetch-Backend-Challenge
A Spring Boot application that tracks point balances for Fetch users across transactions and point spendings.

This project was built using Java 17 and uses the Spring Boot framework for Rest API endpoint functionality. 

## Usage
In order to run the project, you must have Java, Maven, and Git installed.

After cloning the project to your machine, you can install dependencies using "mvn install", and run the application either through your IDE or by using "mvn spring-boot:run".

Functionality testing was performed by using Postman, however you can also interact with the API by using curl to send requests:

curl -X POST http://localhost:8000/api/points/add -d '{"payer": "UNILEVER", "points": 200, "timestamp": "2022-10-31T11:00:00Z"}' -H 'Content-Type: application/json'

curl -X POST http://localhost:8000/api/points/spend -d '{"points": 5000}' -H 'Content-Type: application/json'

curl -X GET http://localhost:8000/api/points/balance 

Thank you.

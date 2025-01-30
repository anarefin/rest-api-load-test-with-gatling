# API Load Testing Project

A performance testing project using Gatling to simulate load on a REST API. This project is set up to test bike rental API endpoints under various load conditions.

## Prerequisites

- Java 21
- Maven
- Docker and Docker Compose (for TimescaleDB)

## Project Structure 

### 2. Run Load Tests

Using Maven wrapper (recommended):

On Unix/Linux/MacOS:
```bash
./mvnw gatling:test
```

On Windows:
```bash
mvnw.cmd gatling:test
```

Or using Maven directly:
```bash
mvn gatling:test
```

### 3. View Results

Test reports are generated in:
```bash
target/gatling/
```
Open the HTML report in your browser to view performance metrics.

## Test Configuration

The load test simulates:
- 20 users ramping up over 10 seconds
- 30 more users over 1 minute
- 50 more users over 30 seconds
- Maintains load for 2 minutes

Endpoint tested:
```
POST http://localhost:8080/bikes?bikeType=city&location=BITS
``` 
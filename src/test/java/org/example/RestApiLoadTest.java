package org.example;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class RestApiLoadTest extends Simulation {

    // Define the HTTP protocol
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Define Scenarios
    private final ScenarioBuilder rampUpScenario = scenario("Bike Rental Ramp Up Test")
            .exec(
                http("Post Request - Ramp Up")
                    .post("/bikes?bikeType=city&location=BITS") 
                    .check(status().is(200))
            );

    private final ScenarioBuilder constantLoadScenario = scenario("Bike Rental Constant Load Test")
            .exec(
                http("Post Request - Constant Load")
                    .post("/bikes?bikeType=city&location=BITS")
                    .check(status().is(200))
            )
            .pause(Duration.ofSeconds(1)); // Add pause to simulate real user behavior

    // Load testing setup
    {
        setUp(
            rampUpScenario.injectOpen(
                rampUsers(20).during(10),   // Ramp up to 20 users in 10 seconds
                rampUsers(30).during(60),   // Ramp up to 50 total in 1 minute
                rampUsers(50).during(30),   // Ramp up to 100 total in 30 seconds
                nothingFor(120)            // Maintain load for 2 minutes
            ),
            constantLoadScenario.injectClosed(
                constantConcurrentUsers(50).during(60)
            )
        ).protocols(httpProtocol);
    }
}

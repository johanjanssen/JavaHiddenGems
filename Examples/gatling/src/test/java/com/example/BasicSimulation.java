package com.example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

// First start the web application
public class BasicSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080");

    ScenarioBuilder scn = scenario("BasicSimulation")
        .repeat(5).on(
             exec(http("car").get("/car")
            .check(status().is(200)))
            .pause(1)
            .exec(http("carpart")
            .get("/carpart")
            .check(status().is(200)))
        );

    {
        setUp(
            scn.injectOpen(rampUsers(8).during(Duration.ofSeconds(10)))
        ).protocols(httpProtocol);
    }
}

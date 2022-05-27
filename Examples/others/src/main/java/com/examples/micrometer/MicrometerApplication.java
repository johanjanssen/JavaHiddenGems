package com.examples.micrometer;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


// http://localhost:8080/actuator/metrics
// http://localhost:8080/actuator/metrics/http.server.requests
@SpringBootApplication
public class MicrometerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicrometerApplication.class, args);
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

}
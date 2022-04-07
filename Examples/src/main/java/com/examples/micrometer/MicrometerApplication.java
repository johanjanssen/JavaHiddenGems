package com.examples.micrometer;

import com.examples.jobrunr.JobrunrStorageConfiguration;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


// http://localhost:8080/actuator/metrics
// http://localhost:8080/actuator/metrics/http.server.requests
@SpringBootApplication
@Import({JobrunrStorageConfiguration.class})
public class MicrometerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicrometerApplication.class, args);
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

}
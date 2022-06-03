package com.example;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Also see application.properties
// http://localhost:8080/actuator/metrics/micrometer.endpoint.counter
// http://localhost:8080/actuator/metrics/micrometer.endpoint.time
// http://localhost:8080/actuator/metrics/micrometer.endpoint.gauge
@RestController
public class MicrometerService {

    @Autowired
    private SimpleMeterRegistry simpleMeterRegistry;

    List<Integer> gaugeList = new ArrayList<>();

    @Timed(value = "micrometer.endpoint.time", description = "Duration of the Micrometer endpoint")
    @GetMapping("/micro")
    public String micro() {
        Counter counter = simpleMeterRegistry.counter("micrometer.endpoint.counter");
        counter.increment();

        int seconds = LocalDateTime.now().getSecond();
        if ((LocalDateTime.now().getSecond() % 2) ==0) {
            gaugeList.add(seconds);
        }

        Gauge gauge = Gauge
                .builder("micrometer.endpoint.gauge", gaugeList, List::size)
                .register(simpleMeterRegistry);
        return "Hello, measuring method duration";
    }
}

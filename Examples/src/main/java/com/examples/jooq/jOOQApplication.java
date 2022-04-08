package com.examples.jooq;

import com.examples.jobrunr.JobrunrStorageConfiguration;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({JobrunrStorageConfiguration.class})
public class jOOQApplication {

    public static void main(String[] args) {
        SpringApplication.run(jOOQApplication.class, args);
    }

}
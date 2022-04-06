package com.examples.shared;

import com.examples.jobrunr.JobrunrStorageConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({JobrunrStorageConfiguration.class})
public class CarApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarApplication.class, args);
    }

}
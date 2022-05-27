package com.examples.togglz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.spi.FeatureProvider;


@SpringBootApplication
public class TogglzApplication {

    public static void main(String[] args) {
        SpringApplication.run(TogglzApplication.class, args);
    }

    @Bean
    public FeatureProvider featureProvider() {
        return new EnumBasedFeatureProvider(TogglzFeatures.class);
    }
}
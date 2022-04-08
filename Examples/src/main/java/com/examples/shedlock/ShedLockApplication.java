package com.examples.shedlock;

import com.examples.jobrunr.JobrunrStorageConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.inmemory.InMemoryLockProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({JobrunrStorageConfiguration.class})
public class ShedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShedLockApplication.class, args);
    }

    @Bean
    public LockProvider lockProvider() {
        return new InMemoryLockProvider();
    }
}
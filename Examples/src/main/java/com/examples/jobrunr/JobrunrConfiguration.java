package com.examples.jobrunr;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.configuration.JobRunrConfiguration;
import org.jobrunr.scheduling.JobRequestScheduler;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.StorageProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobrunrConfiguration {
    @Bean
    public JobRunrConfiguration.JobRunrConfigurationResult initJobRunr(StorageProvider storageProvider, ApplicationContext applicationContext) {
        return JobRunr.configure()
                .useJobActivator(applicationContext::getBean)
                .useStorageProvider(storageProvider)
                .useBackgroundJobServer()
                .useJmxExtensions()
                .useDashboard()
                .initialize();
    }

    @Bean
    public JobScheduler initJobScheduler(JobRunrConfiguration.JobRunrConfigurationResult jobRunrConfigurationResult) {
        return jobRunrConfigurationResult.getJobScheduler();
    }

    @Bean
    public JobRequestScheduler initJobRequestScheduler(JobRunrConfiguration.JobRunrConfigurationResult jobRunrConfigurationResult) {
        return jobRunrConfigurationResult.getJobRequestScheduler();
    }
}

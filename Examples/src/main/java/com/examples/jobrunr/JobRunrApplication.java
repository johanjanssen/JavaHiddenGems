package com.examples.jobrunr;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.configuration.JobRunrConfiguration;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.JobRequestScheduler;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JobRunrApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobRunrApplication.class, args);
    }

    @Job(name = "The sample job with variable %0", retries = 2)
    public void annotatedJob(String variable) {
        System.out.println("Annotated job " + variable);
    }

    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }

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

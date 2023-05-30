package com.examples.jobrunr;

import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.spring.annotations.Recurring;
import org.springframework.stereotype.Service;

@Service
public class JobRunrService {

    @Job(name = "The sample job with variable %0", retries = 1)
    public void annotatedJob(String variable) throws Exception {
        System.out.println("Annotated job " + variable);
        throw new Exception("Trigger retry");
    }

    @Recurring(id="a-recurring-job", cron="* * * * *")
    @Job(name = "My Recurring job")
    public void aRecurringJob() {
        System.out.println("This wil run every minute");
    }
}

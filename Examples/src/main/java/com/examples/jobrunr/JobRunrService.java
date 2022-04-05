package com.examples.jobrunr;

import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;

@Service
public class JobRunrService {
    @Job(name = "The sample job with variable %0", retries = 1)
    public void annotatedJob(String variable) throws Exception {
        System.out.println("Annotated job " + variable);
        throw new Exception();
    }

}

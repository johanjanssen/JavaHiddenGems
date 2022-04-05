package com.examples;

import com.examples.jobrunr.JobRunrApplication;
import com.examples.jobrunr.JobRunrService;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import java.time.LocalDateTime;

// Also see http://localhost:8001/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= JobRunrApplication.class)
public class JobRunrTest {

    @Inject
    private JobScheduler jobScheduler;

    @Inject
    private JobRunrService jobRunrService;

    @Test
    void testJobRunr() throws InterruptedException {

        jobScheduler.enqueue(() -> System.out.println(" Enqueued job"));

        jobScheduler.schedule(LocalDateTime.now().plusSeconds(2), () -> System.out.println(" Scheduled job"));

        jobScheduler.scheduleRecurrently(Cron.every15seconds(), () -> System.out.println("Recurring job"));

        jobScheduler.enqueue(() -> jobRunrService.annotatedJob("retry"));
        Thread.sleep(60000);
    }
}

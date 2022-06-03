package com.example;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@EnableSchedulerLock(defaultLockAtMostFor = "10m")
@EnableScheduling
@Service
public class ShedLockService {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(cron = "0/5 * * * * *")//Every 5 seconds
    public void schedule1(){
        System.out.println("            Default scheduling 1 " + LocalTime.now().format(dateTimeFormatter).toString());
    }

    @Scheduled(cron = "0/5 * * * * *")//Every 5 seconds
    public void schedule2(){
        System.out.println("            Default scheduling 2 " + LocalTime.now().format(dateTimeFormatter).toString());
    }

    @Scheduled(cron = "0/5 * * * * *")//Every 5 seconds
    @SchedulerLock(name = "taskName", lockAtMostFor = "14m", lockAtLeastFor = "4s")
    public void scheduledTask1() {
        System.out.println("ShedLock method 1 " + LocalTime.now().format(dateTimeFormatter).toString());

    }

    @Scheduled(cron = "0/5 * * * * *")//Every 5 seconds
    @SchedulerLock(name = "taskName", lockAtMostFor = "14m", lockAtLeastFor = "4s")
    public void scheduledTask2() {
        System.out.println("ShedLock method 2 " + LocalTime.now().format(dateTimeFormatter).toString());
    }
}

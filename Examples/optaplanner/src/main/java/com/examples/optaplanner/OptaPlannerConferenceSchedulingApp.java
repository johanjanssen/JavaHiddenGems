package com.examples.optaplanner;

import com.examples.optaplanner.domain.Room;
import com.examples.optaplanner.domain.Session;
import com.examples.optaplanner.domain.Timeslot;
import com.examples.optaplanner.persistence.RoomRepository;
import com.examples.optaplanner.persistence.SessionRepository;
import com.examples.optaplanner.persistence.TimeslotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.DayOfWeek;
import java.time.LocalTime;

@SpringBootApplication
public class OptaPlannerConferenceSchedulingApp {

    public static void main(String[] args) {
        SpringApplication.run(OptaPlannerConferenceSchedulingApp.class, args);
    }

    @Bean
    public CommandLineRunner demoData(
            TimeslotRepository timeslotRepository,
            RoomRepository roomRepository,
            SessionRepository sessionRepository) {
        return (args) -> {
            timeslotRepository.save(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 15)));
            timeslotRepository.save(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(11, 30), LocalTime.of(12, 15)));
            timeslotRepository.save(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 00), LocalTime.of(14, 45)));
            timeslotRepository.save(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 15), LocalTime.of(16, 00)));
            timeslotRepository.save(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 15), LocalTime.of(17, 00)));

            roomRepository.save(new Room("Progress"));
            roomRepository.save(new Room("Mission 1"));
            roomRepository.save(new Room("Mission 2"));
            roomRepository.save(new Room("Expedition"));

            sessionRepository.save(new Session("Kubernetes: All your clouds belong to us", "Burr Sutter", "English"));
            sessionRepository.save(new Session("What you can learn from Connect’s use of Kafka", "Kate Stanley", "English"));
            sessionRepository.save(new Session("What the CRaC – Superfast JVM startup", "Gerrit Grunwald", "English"));
            sessionRepository.save(new Session("Robust integration patterns for Spring microservices", "Joris Kuipers", "Dutch"));
            sessionRepository.save(new Session("Wired! How your brain learns new (programming) languages", "Simone de Gijt", "Dutch"));
            sessionRepository.save(new Session("Streamlining large-scale Java development using Error Prone", "Sander Mak", "Dutch"));
            sessionRepository.save(new Session("Sander's second awesome session", "Sander Mak", "Dutch"));
            sessionRepository.save(new Session("Sander's third awesome session", "Sander Mak", "Dutch"));
            sessionRepository.save(new Session("Sander's fourth awesome session", "Sander Mak", "Dutch"));
            sessionRepository.save(new Session("Mastering Testcontainers for better integration tests", "Oleg Selajev ", "English"));
            sessionRepository.save(new Session("The 5 basic topics you should know about project reactor", "Erwin Manders", "Dutch"));
            sessionRepository.save(new Session("Java’s Hidden Gems: Tools and Libraries", "Johan Janssen", "Dutch"));
            sessionRepository.save(new Session("Use Testing to Develop Better Software Faster", "Marit van Dijk", "Dutch"));
            sessionRepository.save(new Session("Hacking for developers: a live demonstration", "Coen Goedegebure", "Dutch"));
            sessionRepository.save(new Session("Greencode: Sustainable Java architectures for low power consumption and better cost/performance", "Vinicius Senger", "English"));
            sessionRepository.save(new Session("Modelling universal values", "Peter Hilton", "Dutch"));
            sessionRepository.save(new Session("Reactive Java Microservices on Kubernetes with Spring WebFlux, Spring Cloud and JHipster", "Deepu K Sasidharan", "English"));
            sessionRepository.save(new Session("Lessons Learned: Going from Floppy Disks to Cloud Deployments", "Martijn Dashorst", "Dutch"));
            sessionRepository.save(new Session("DevOps for Java developers", "Ixchel Ruiz", "English"));
            sessionRepository.save(new Session("Learning Through Tinkering", "Tom Cools", "Dutch"));
            sessionRepository.save(new Session("Diabolical Developers Guide to JVM Ergonomics on Containers and K8s", "Martijn Verburg", "English"));
            sessionRepository.save(new Session("Community hacks to enhance your career", "Sam Hepburn", "English"));
        };
    }


}

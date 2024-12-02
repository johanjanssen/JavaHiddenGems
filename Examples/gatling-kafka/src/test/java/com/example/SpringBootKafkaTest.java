package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Testcontainers
class SpringBootKafkaTest {
    @Container
    private static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka-native:3.8.0").asCompatibleSubstituteFor("apache/kafka"));

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    private String response;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "response-topic", groupId = "test-groupId")
    public void receive(String string) {
        response = string;
        System.out.println("Received from response topic in test " + response);
    }

    @Test
    public void testNotificationSending() throws InterruptedException {
        kafkaTemplate.send("request-topic", "Send to request topic in test: blablabla");

        await().atMost(5, SECONDS).until(() -> response != null);
        assertEquals("Send to response topic Send to request topic in test: blablabla", response);
    }

}

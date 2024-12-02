package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "request-topic", groupId = "test-groupId")
    public void receive(String string) {
        System.out.println("Received from request topic " + string);
        kafkaTemplate.send("response-topic", "Send to response topic " + string);
    }
}
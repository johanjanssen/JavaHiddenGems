package com.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AwaitilityTest {
    private List<String> exampleList = new ArrayList<>();
    void addToList(String value) {
        exampleList.add(value);
    }

    @Test
    void testAsynchronousOperation() throws InterruptedException {
        long start = System.currentTimeMillis();

        assertEquals(0, exampleList.size());

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            addToList("Test " + Math.random());
        });
        assertEquals(0, exampleList.size());

        await().atMost(2, SECONDS).until(() -> exampleList.size() == 1);

        assertEquals(1, exampleList.size());

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Duration with awaitility: " + timeElapsed);
    }

    @Test
    void testAsynchronousOperationWithThreadSleep() throws InterruptedException {
        long start = System.currentTimeMillis();

        assertEquals(0, exampleList.size());

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            addToList("Test " + Math.random());
        });
        assertEquals(0, exampleList.size());
        Thread.sleep(2000);
        assertEquals(1, exampleList.size());

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Duration with Thread.sleep(): " + timeElapsed);
    }
}

package com.examples;

import org.junit.jupiter.api.Test;
import org.moditect.jfrunit.EnableEvent;
import org.moditect.jfrunit.JfrEventTest;
import org.moditect.jfrunit.JfrEvents;
import org.moditect.jfrunit.events.GarbageCollection;
import org.moditect.jfrunit.events.JfrEventTypes;
import org.moditect.jfrunit.events.ThreadSleep;

import java.time.Duration;

import static org.moditect.jfrunit.ExpectedEvent.event;
import static org.moditect.jfrunit.JfrEventsAssert.assertThat;


@JfrEventTest
public class JfrUnitTest {

    public JfrEvents jfrEvents = new JfrEvents();

    @Test
    @EnableEvent(GarbageCollection.EVENT_NAME)
    @EnableEvent(ThreadSleep.EVENT_NAME)
    public void shouldHaveGcAndSleepEvents() throws Exception {
        System.gc();
        Thread.sleep(42);

        jfrEvents.awaitEvents();

        assertThat(jfrEvents).contains(JfrEventTypes.GARBAGE_COLLECTION);
        assertThat(jfrEvents)
                .contains(event("jdk.ThreadSleep")
                        .with("time", Duration.ofMillis(42)));
    }
}
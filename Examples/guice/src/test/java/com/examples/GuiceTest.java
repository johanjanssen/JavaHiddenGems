package com.examples;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuiceTest {
    @Test
    void testGuice() {
        Injector injector = Guice.createInjector();

        BookService bookService = injector.getInstance(BookService.class);

        assertEquals("Much richer The Hobbit", bookService.handleBook());
    }
}

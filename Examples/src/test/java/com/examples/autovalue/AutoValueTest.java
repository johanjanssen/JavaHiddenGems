package com.examples.autovalue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AutoValueTest {

    class Book {
        private String author;
        private String title;

        public Book(String author, String title) {
            this.author = author;
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }
    }

    @Test
    void traditionalClassTest() {
        Book book1 = new Book("Craig Walls", "Spring Boot In Action");
        assertEquals("Craig Walls", book1.getAuthor());

        Book book2 = new Book("Craig Walls", "Spring Boot In Action");
        assertFalse(book1.equals(book2)); // only compares object reference, not content
    }

    @Test
    void autoValueTest() {
        AutoValueBook book1 = AutoValueBook.create("Craig Walls", "Spring Boot In Action");
        assertEquals("Craig Walls", book1.author());

        AutoValueBook book2 = AutoValueBook.create("Craig Walls", "Spring Boot In Action");
        assertTrue(book1.equals(book2));
    }
}

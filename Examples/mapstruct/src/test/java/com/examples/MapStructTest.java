package com.examples;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Fist compile the project
// Also see the annotation processor in the maven-compiler-plugin
public class MapStructTest {
    @Test
    void mapBookEntityToBook() {
        BookEntity bookEntity = new BookEntity(0, "Spring Boot In Action", "Answer to the Ultimate Question of Life, the Universe, and Everything");

        Book book = BookMapper.INSTANCE.bookEntityToBook(bookEntity);

        assertEquals("Spring Boot In Action", book.getTitle());
        assertEquals("Answer to the Ultimate Question of Life, the Universe, and Everything", book.getBookValue());
    }
}

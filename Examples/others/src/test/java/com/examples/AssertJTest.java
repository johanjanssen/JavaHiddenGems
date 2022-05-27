package com.examples;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssertJTest {
    record Book(String title, String author) {

    }

    @Test
    void testStudent() {
        Book springBootBook = new Book("Spring Boot in Action", "Craig Walls");
        Book concurrencyBook = new Book("Java Concurrency in Practice", "Brian Goetz");
        List<Book> bookList = List.of(springBootBook, concurrencyBook);

        assertThat(springBootBook.title).isEqualTo("Spring Boot in Action");
        assertThat(springBootBook.title).isNotEqualTo(null);
        assertThat(concurrencyBook.title).startsWith("Java")
                .endsWith("Practice")
                .isEqualToIgnoringCase("java concurrency in practice");

        assertThat(bookList).hasSize(2)
                .contains(springBootBook, concurrencyBook)
                .doesNotContain(new Book("bla", "bla"));

        assertThat(bookList).extracting(Book::author).containsExactly("Craig Walls", "Brian Goetz")
                .doesNotContain("Johan");

        assertThat(bookList).filteredOn(book -> book.author.startsWith("B"))
                .containsOnly(concurrencyBook)
                .extracting(Book::title)
                .contains("Java Concurrency in Practice");

        assertThatThrownBy(() -> {throw new Exception("Oh no!");}).hasMessage("Oh no!");
    }
}

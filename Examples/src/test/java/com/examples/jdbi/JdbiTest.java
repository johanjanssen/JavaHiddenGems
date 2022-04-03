package com.examples.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbiTest {

    @Test
    void testDB() {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test"); // (H2 in-memory database)

        List<Book> bookList = jdbi.withHandle(handle -> {
            handle.execute("CREATE TABLE \"book\" (id INTEGER PRIMARY KEY, \"title\" VARCHAR)");

            handle.createUpdate("INSERT INTO \"book\" (id, \"title\") VALUES (:id, :title)")
                    .bindBean(new Book(0, "Spring Boot In Action"))
                    .execute();

            handle.createUpdate("INSERT INTO \"book\" (id, \"title\") VALUES (:id, :title)")
                    .bindBean(new Book(1, "Java Concurrency in Practice"))
                    .execute();

            return handle.createQuery("SELECT * FROM \"book\" ORDER BY \"title\"")
                    .mapToBean(Book.class)
                    .list();
        });

        for (Book book: bookList) {
            System.out.println(book.getTitle());
        }
        assertEquals(2, bookList.size());
        assertEquals("Java Concurrency in Practice", bookList.get(0).getTitle());
        assertEquals("Spring Boot In Action", bookList.get(1).getTitle());
    }
}

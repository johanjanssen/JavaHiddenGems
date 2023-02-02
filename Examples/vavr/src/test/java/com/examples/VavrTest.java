package com.examples;

import io.vavr.Function8;
import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class VavrTest {

    @Test
    void testVavrOptions() {
        Option<Object> nullOption = Option.of(null);
        Option<Object> realValueOption = Option.of("42");

        assertEquals("None", nullOption.toString());
        assertEquals("Some(42)", realValueOption.toString());

        assertEquals("", nullOption.getOrElse(""));
        assertEquals("42", realValueOption.getOrElse(""));

        assertEquals("Alternative", nullOption.getOrElse("Alternative"));
        assertEquals("42", realValueOption.getOrElse("42"));

        Tuple2<Integer, String> answer = Tuple.of(42,"Answer to the Ultimate Question of Life, the Universe, and Everything");
        assertEquals(42, answer._1);
        assertEquals("Answer to the Ultimate Question of Life, the Universe, and Everything", answer._2);

        Try<Integer> result = Try.of(() -> 42 / 0);
        assertTrue(result.isFailure());
        assertEquals("java.lang.ArithmeticException: / by zero", result.failed().get().toString());

        // Functional interfacces
        Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> multiply =
                (a, b, c, d, e, f, g, h) -> a * b * c * d * e * f * g * h;
        Integer multiplication = multiply.apply(1, 2, 3, 5, 8, 13, 21, 34);
        assertEquals(2227680, multiplication);

        // Immutable collections
        List<String> bookList = List.of("book1", "book2");
        assertEquals(2, bookList.size());
        assertEquals(new String("book2"), bookList.get(1));

        String helloworld = "hello world";
        Lazy<String> lazy = Lazy.of(() -> helloworld.toUpperCase(Locale.ROOT));
        assertFalse(lazy.isEvaluated());

        assertEquals("HELLO WORLD", lazy.get());
        assertTrue(lazy.isEvaluated());
    }
}

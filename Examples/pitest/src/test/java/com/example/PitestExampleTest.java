package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PitestExampleTest {
    private PitestExample pitestExample = new PitestExample(42);

    @Test
    public void testPitest() {
        assertEquals(42, pitestExample.higherOrVariable(2,5));
    }
}

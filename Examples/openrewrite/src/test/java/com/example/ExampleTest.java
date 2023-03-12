package com.example;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class ExampleTest {

    @Test
    public void simpleTest() {
        Assert.assertEquals(42, 42);
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void temporaryFileTest() throws IOException {
        File testFile = folder.newFile("test.txt");
        Assert.assertTrue(testFile.exists());
    }

    @Test(expected = IOException.class)
    public void expectedExceptionTest() throws IOException {
        maybeThrowException(true);
    }

    private void maybeThrowException(boolean maybe) throws IOException {
        if (maybe) {
            throw new IOException();
        }
    }
}

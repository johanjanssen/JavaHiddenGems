package com.examples;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;
import pl.touk.tscreload.Reloadable;
import pl.touk.tscreload.TscReloadableConfigFactory;

import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeSafeConfigTest {

    @Test
    void testConfig() throws InterruptedException {
        System.setProperty("typesafe-config.answer", "42 from a system property");

        Config configuration = ConfigFactory.load();

        assertEquals("42 from a system property", configuration.getString("typesafe-config.answer"));
        assertEquals("? from application.properties", configuration.getString("typesafe-config.question"));

        // Optional hot reload config with extra dependency
        Reloadable<Config> configReloadable = TscReloadableConfigFactory.parseFile(new File("src/test/resources/application.properties"), Duration.ofSeconds(1));
        //Reloadable<Integer> integerReloadable = configReloadable.map(c -> c.getInt("typesafe-config.changevalue"));



        for (int i = 0; i < 10; i++) {
            System.out.println("Default configuration: " + configuration.getInt("typesafe-config.changevalue"));
            System.out.println("Hot reload configuration: " + configReloadable.map(c -> c.getInt("typesafe-config.changevalue")).currentValue());
            Thread.sleep(5000);
        }
    }
}

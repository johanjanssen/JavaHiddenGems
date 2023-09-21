package com.example;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// see test/webapp/WEB-INF
public class PebbleTemplatesTest {
    record Expertise(String technology, int years) {}
    record Person(String firstName, String lastName) {}

    @Test
    void testPebbleTemplates() throws IOException {
        PebbleEngine engine = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("specificFunction.html");

        Map<String, Object> context = new HashMap<>();
        context.put("firstName", "{{}}");
        context.put("lastName", "Janssen");

        context.put("functionTitle", "Senior vice president principal code ninja turtle warrior architect");

        Random random = new Random();
        Expertise expertise1 = new Expertise("GraalVM", 20);
        Expertise expertise2 = new Expertise("PHP", random.nextInt(8));
        Expertise expertise3 = new Expertise("Spring Boot", random.nextInt(8));
        Expertise expertise4 = new Expertise("J2EE (yes I know it should be Java EE or Jakarta EE ;))", random.nextInt(15));
        List<Expertise> expertiseList = List.of(expertise1, expertise2, expertise3, expertise4);
        context.put("expertiseList", expertiseList);

        context.put("workingHours", (int)(Math.random()*(60-40))+40);
        context.put("vacationDays", 20);

        context.put("salary", random.nextInt(2000));


        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        System.out.println(writer.toString());
    }
}

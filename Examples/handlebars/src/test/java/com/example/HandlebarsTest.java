package com.example;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

// see test/resources/HandlebarsExample.hbs
public class HandlebarsTest {
    record Expertise(String technology, int years) {}
    record Person(String firstName, String lastName) {}
    record Vacancy(String title, Person person, String functionTitle, List<Expertise> expertiseList, int salary, int workinghours, int vacationdays) {}

    @Test
    void testHandlebars() throws IOException {
        String classpath = System.getProperty("java.class.path");
        String[] classPathValues = classpath.split(File.pathSeparator);
        System.out.println(Arrays.toString(classPathValues));

        Handlebars handlebars = new Handlebars();

        Template template = handlebars.compile("HandlebarsExample");

        Random random = new Random();
        Person person = new Person("{{}}", "Janssen");
        Expertise expertise1 = new Expertise("GraalVM", 20);
        Expertise expertise2 = new Expertise("PHP", random.nextInt(8));
        Expertise expertise3 = new Expertise("Spring Boot", random.nextInt(8));
        Expertise expertise4 = new Expertise("J2EE (yes I know it should be Java EE or Jakarta EE ;))", random.nextInt(15));
        List<Expertise> expertiseList = List.of(expertise1, expertise2, expertise3, expertise4);
        Vacancy vacancy = new Vacancy("New super duper function", person, "Senior vice president principal code ninja turtle warrior architect", expertiseList, random.nextInt(2000), (int)(Math.random()*(60-40))+40, 20);
        String templateString = template.apply(vacancy);

        System.out.println(templateString);
        assertTrue(templateString.contains("Janssen"));
    }
}

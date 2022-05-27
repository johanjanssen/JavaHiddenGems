package com.example;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = jMoleculesApplication.class)
@RequiredArgsConstructor
class jMoleculesTest {

    private final ConfigurableApplicationContext context;

    @Test
    void testjMolecules() {
        Address address = new Address("42");
        Students students = context.getBean(Students.class);
        Student storedStudent = students.save(new Student("James", List.of(address)));
        Optional<Student> retrievedStudent = students.findById(storedStudent.getId());
        assertEquals("James", retrievedStudent.get().getName());
    }
}
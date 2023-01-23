package com.example;


import com.example.Student.StudentId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Value;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//No need for Constructor, @Entity, @EmbeddedId, @OneToMany, serialVersionUID
@Getter
public class Student implements AggregateRoot<Student, StudentId> {

    private final StudentId id;
    private String name;
    private List<Address> addressList = new ArrayList<>();

    public Student(String name, List<Address> addressList) {
        this.id = StudentId.of(UUID.randomUUID().toString());
        this.name = name;
        this.addressList = addressList;
    }

    @Value(staticConstructor = "of")
    public static class StudentId implements Identifier {
        private final String id;
    }
}

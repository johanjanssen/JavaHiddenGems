package com.example;

import com.example.Student.StudentId;

import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.repository.CrudRepository;


public interface Students extends CrudRepository<Student, StudentId>, AssociationResolver<Student, StudentId> {
}
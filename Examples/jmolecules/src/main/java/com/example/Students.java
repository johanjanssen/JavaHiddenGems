package com.example;

import com.example.Student.StudentId;

import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface Students extends CrudRepository<Student, StudentId>, AssociationResolver<Student, StudentId> {
}
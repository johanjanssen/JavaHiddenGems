package com.examples.jmolecules;

import com.examples.jmolecules.Student.StudentId;

import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.repository.CrudRepository;

public interface Students extends CrudRepository<Student, StudentId>, AssociationResolver<Student, StudentId> {
}
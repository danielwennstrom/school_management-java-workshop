package org.example.data_access;

import org.example.model.StudentImpl;

import java.util.*;

public interface StudentDAO {
    StudentImpl saveStudent(StudentImpl student);
    StudentImpl findByEmail(String email);
    StudentImpl findById(int id);
    Collection<StudentImpl> findByName(String name);
    Collection<StudentImpl> findAll();
    boolean deleteStudent(StudentImpl student);
}

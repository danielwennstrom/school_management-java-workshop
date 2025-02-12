package org.example.data_access;

import org.example.model.StudentImpl;

import java.util.*;

public interface StudentDAO {
    StudentImpl saveStudent(StudentImpl student);
    StudentImpl findByEmail(String email);
    List<StudentImpl> findByName(String name);
    StudentImpl findById(int id);
    List<StudentImpl> findAll();
    boolean deleteStudent(StudentImpl student);
}

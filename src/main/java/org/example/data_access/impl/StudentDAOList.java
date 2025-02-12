package org.example.data_access.impl;

import org.example.data_access.StudentDAO;
import org.example.model.Student;

import java.util.List;

public class StudentDAOList implements StudentDAO {
    @Override
    public Student saveStudent(Student student) {
        return null;
    }

    @Override
    public Student findByEmail(String email) {
        return null;
    }

    @Override
    public List<Student> findByName(String name) {
        return List.of();
    }

    @Override
    public Student findById(int id) {
        return null;
    }

    @Override
    public List<Student> findAll() {
        return List.of();
    }

    @Override
    public boolean deleteStudent(Student student) {
        return false;
    }
}

package org.example.data_access.impl;

import org.example.data_access.StudentDAO;
import org.example.model.StudentImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentDAOList implements StudentDAO {
    private static Set<StudentImpl> students = new HashSet<>();

    @Override
    public StudentImpl saveStudent(StudentImpl student) {
        return null;
    }

    @Override
    public StudentImpl findByEmail(String email) {
        return null;
    }

    @Override
    public List<StudentImpl> findByName(String name) {
        return List.of();
    }

    @Override
    public StudentImpl findById(int id) {
        return null;
    }

    @Override
    public List<StudentImpl> findAll() {
        return List.of();
    }

    @Override
    public boolean deleteStudent(StudentImpl student) {
        return false;
    }
}

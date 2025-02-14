package org.example.data_access.impl;

import org.example.data_access.StudentDAO;
import org.example.model.StudentImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StudentDAOSet implements StudentDAO {
    private Set<StudentImpl> students = new HashSet<>();
    private static volatile StudentDAOSet instance;

    public static StudentDAOSet getInstance() {
        if (instance == null) {
            synchronized (StudentDAOSet.class) {
                if (instance == null)
                    instance = new StudentDAOSet();
            }
        }
        return instance;
    }

    @Override
    public StudentImpl saveStudent(StudentImpl student) {
        if (student == null)
            throw new IllegalArgumentException("Student can't be null");

        StudentImpl existingStudent = findById(student.getId());
        if (existingStudent == null) {
            students.add(student);
            return student;
        }

        students.remove(existingStudent);

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setCourses(student.getCourses());

        students.add(existingStudent);

        return existingStudent;
    }

    @Override
    public StudentImpl findByEmail(String email) {
        return students.stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<StudentImpl> findByName(String name) {
        return students.stream()
                .filter(s -> s.getName().contains(name))
                .toList();
    }

    @Override
    public StudentImpl findById(int id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<StudentImpl> findAll() {
        return Set.copyOf(students);
    }

    @Override
    public boolean deleteStudent(StudentImpl student) {
        if (student == null) {
            throw new IllegalArgumentException("Student can't be null");
        }
        if (!students.contains(student)) {
            throw new IllegalStateException("Student not found in the list");
        }

        students.remove(student);
        return true;
    }
}

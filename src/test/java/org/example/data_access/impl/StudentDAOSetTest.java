package org.example.data_access.impl;

import org.example.model.LectureImpl;
import org.example.model.StudentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDAOSetTest {

    private StudentDAOSet dao;
    private StudentImpl student1;
    private StudentImpl student2;

    @BeforeEach
    void setUp() {
        student1 = new StudentImpl("Sven Svensson", "s.svensson@example.com", "01234");
        student2 = new StudentImpl("Erik Eriksson", "e.eriksson@example.com", "56789");

        dao = StudentDAOSet.getInstance();
    }

    @Test
    void getInstance() {
        assertNotNull(StudentDAOSet.getInstance());
    }

    @Test
    void saveStudent() {
        StudentImpl savedStudent1 = dao.saveStudent(student1);
        assertNotNull(savedStudent1);
        assertEquals("Sven Svensson", savedStudent1.getName());

        student1.setName("Updated Sven Svensson");
        StudentImpl updatedSavedStudent1 = dao.saveStudent(student1);
        assertEquals("Updated Sven Svensson", updatedSavedStudent1.getName());
        dao.deleteStudent(updatedSavedStudent1);
    }

    @Test
    void findByEmail() {
        dao.saveStudent(student1);
        StudentImpl foundStudent = dao.findByEmail(student1.getEmail());
        assertNotNull(foundStudent);
        assertEquals("Sven Svensson", foundStudent.getName());
    }

    @Test
    void findByName() {
        dao.saveStudent(student1);
        Collection<StudentImpl> foundStudent = dao.findByName(student1.getName());
        assertTrue(foundStudent.stream()
                .anyMatch(s -> s.getId() == student1.getId()));
    }

    @Test
    void findById() {
        dao.saveStudent(student1);
        StudentImpl foundStudent = dao.findById(student1.getId());
        assertNotNull(foundStudent);
        assertEquals("Sven Svensson", foundStudent.getName());
    }

    @Test
    void findAll() {
        dao.saveStudent(student1);
        dao.saveStudent(student2);
        Collection<StudentImpl> allStudents = dao.findAll();
        assertEquals(2, allStudents.size());
    }

    @Test
    void deleteStudent() {
        dao.saveStudent(student1);
        boolean result = dao.deleteStudent(student1);
        assertTrue(result);

        StudentImpl deletedStudent = dao.findById(student1.getId());
        assertNull(deletedStudent);
    }
}
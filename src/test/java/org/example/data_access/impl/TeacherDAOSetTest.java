package org.example.data_access.impl;

import org.example.model.CourseImpl;
import org.example.model.TeacherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherDAOSetTest {
    private TeacherImpl teacher1;
    private CourseImpl course;
    private TeacherDAOSet dao;

    @BeforeEach
    void setUp() {
        teacher1 = new TeacherImpl("Sven Svensson", "sven.svensson@example.com", "1234567890");
        course = new CourseImpl("Java Programming", LocalDate.now());
        dao = TeacherDAOSet.getInstance();
        dao.saveTeacher(teacher1);
    }

    @Test
    void getInstance() {
        assertNotNull(dao);
    }

    @Test
    void saveTeacher() {
        TeacherImpl returnedTeacher = dao.saveTeacher(teacher1);
        assertEquals(returnedTeacher, teacher1);
    }

    @Test
    void findById() {
        TeacherImpl foundTeacher = dao.findById(teacher1.getId());
        assertEquals(foundTeacher, teacher1);
    }

    @Test
    void findByEmail() {
        TeacherImpl foundTeacher = dao.findByEmail(teacher1.getEmail());
        assertEquals(foundTeacher.getName(), teacher1.getName());
    }

    @Test
    void findByName() {
        Collection<TeacherImpl> foundTeachers = dao.findByName("Sven Svensson");
        assertTrue(foundTeachers.stream()
                .anyMatch(t -> t.getName().equals(teacher1.getName())));
    }

    @Test
    void findTeacherByCourse() {
        course.registerTeacher(teacher1);

        TeacherImpl foundTeacher = dao.findTeacherByCourse(course);
        assertEquals(foundTeacher, teacher1);
    }

    @Test
    void deleteTeacher() {
        boolean result = dao.deleteTeacher(teacher1);
        assertTrue(result);
    }
}
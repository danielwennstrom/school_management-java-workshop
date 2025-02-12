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

    @BeforeEach
    void setUp() {
        teacher1 = new TeacherImpl("Sven Svensson", "sven.svensson@example.com", "1234567890");
        course = new CourseImpl("Java Programming", LocalDate.now());
        TeacherDAOSet.getInstance().saveTeacher(teacher1);
    }

    @Test
    void getInstance() {
        assertNotNull(TeacherDAOSet.getInstance());
    }

    @Test
    void saveTeacher() {
        TeacherImpl returnedTeacher = TeacherDAOSet.getInstance().saveTeacher(teacher1);
        assertEquals(returnedTeacher, teacher1);
    }

    @Test
    void findById() {
        TeacherImpl foundTeacher = TeacherDAOSet.getInstance().findById(teacher1.getId());
        assertEquals(foundTeacher, teacher1);
    }

    @Test
    void findByEmail() {
        TeacherImpl foundTeacher = TeacherDAOSet.getInstance().findByEmail(teacher1.getEmail());
        assertEquals(foundTeacher.getName(), teacher1.getName());
    }

    @Test
    void findByName() {
        Collection<TeacherImpl> foundTeachers = TeacherDAOSet.getInstance().findByName("Sven Svensson");
        assertTrue(foundTeachers.stream()
                .anyMatch(t -> t.getName().equals(teacher1.getName())));
    }

    @Test
    void findTeacherByCourse() {
        course.registerTeacher(teacher1);

        TeacherImpl foundTeacher = TeacherDAOSet.getInstance().findTeacherByCourse(course);
        assertEquals(foundTeacher, teacher1);
    }

    @Test
    void deleteTeacher() {
        boolean result = TeacherDAOSet.getInstance().deleteTeacher(teacher1);
        assertTrue(result);
    }
}
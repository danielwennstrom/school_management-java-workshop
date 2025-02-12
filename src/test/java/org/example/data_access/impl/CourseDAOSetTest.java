package org.example.data_access.impl;

import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collection;

class CourseDAOSetTest {
    private CourseImpl course;
    private CourseDAOSet dao;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        date = LocalDate.parse("2025-02-12");
        course = new CourseImpl("Java Programming", date);
        dao = CourseDAOSet.getInstance();
        dao.saveCourse(course);
    }

    @Test
    void getInstance() {
        assertNotNull(dao);
    }

    @Test
    void saveCourse() {
        CourseImpl savedCourse = dao.saveCourse(course);
        assertNotNull(savedCourse);
        assertEquals("Java Programming", savedCourse.getCourseName());

        savedCourse.setCourseName("Updated Java Programming");
        dao.saveCourse(savedCourse);
        assertNotNull(savedCourse);
        assertEquals("Updated Java Programming", savedCourse.getCourseName());
    }

    @Test
    void findById() {
        CourseImpl foundCourse = dao.findById(course.getId());
        assertNotNull(foundCourse);
    }

    @Test
    void findByName() {
        Collection<CourseImpl> foundCourses = dao.findByName("Java Programming");
        assertTrue(foundCourses.stream()
                .anyMatch(t -> t.getCourseName().equals(course.getCourseName())));
    }

    @Test
    void findByDate() {
        Collection<CourseImpl> foundCourses = dao.findByDate(date);
        assertTrue(foundCourses.stream().anyMatch(c -> c.getStartDate().equals(course.getStartDate())));
    }

    @Test
    void findAll() {
        Collection<CourseImpl> allCourses = dao.findAll();
        assertNotNull(allCourses);
    }

    @Test
    void removeCourse() {
        boolean result = dao.removeCourse(course);
        assertTrue(result);
    }
}
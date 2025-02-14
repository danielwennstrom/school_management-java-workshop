package org.example.data_access.impl;

import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class LectureDAOSetTest {

    private TeacherImpl teacher1;
    private LectureDAOSet dao;
    private LectureImpl lecture1, lecture2;
    private CourseImpl course;

    @BeforeEach
    void setUp() {
        teacher1 = new TeacherImpl("Sven Svensson", "sven.svensson@example.com", "1234567890");
        LocalDate date = LocalDate.parse("2025-02-12");
        lecture1 = new LectureImpl("Lecture 1");
        lecture2 = new LectureImpl("Lecture 2");
        course = new CourseImpl("Java Programming", date);

        dao = LectureDAOSet.getInstance();
    }

    @Test
    void getInstance() {
        assertNotNull(LectureDAOSet.getInstance());
    }

    @Test
    void saveLecture() {
        LectureImpl savedLecture = dao.saveLecture(lecture1);
        assertNotNull(savedLecture);
        assertEquals("Lecture 1", savedLecture.getLectureName());

        lecture1.setLectureName("Updated Lecture 1");
        LectureImpl updatedSavedLecture = dao.saveLecture(lecture1);
        assertEquals("Updated Lecture 1", updatedSavedLecture.getLectureName());
    }

    @Test
    void findById() {
        dao.saveLecture(lecture1);
        LectureImpl foundLecture = dao.findById(lecture1.getId());
        assertNotNull(foundLecture);
        assertEquals("Lecture 1", foundLecture.getLectureName());
    }

    @Test
    void findByTeacher() {
        lecture1.registerTeacher(teacher1);
        Collection<LectureImpl> foundLectures = dao.findByTeacher(teacher1);
        assertTrue(foundLectures.stream()
                .anyMatch(l -> l.getId() == lecture1.getId()));
    }

    @Test
    void findByCourse() {
        CourseDAOSet courseDao = (CourseDAOSet) CourseDAOSet.getInstance();
        courseDao.saveCourse(course);

        Collection<CourseImpl> courses = courseDao.findAll();
        assertTrue(courses.stream().anyMatch(c -> c.getId() == course.getId()));

        course.registerLecture(lecture1);
        course.registerLecture(lecture2);
        Collection<LectureImpl> foundLectures = dao.findByCourse(course);
        assertTrue(foundLectures.stream()
                .anyMatch(l -> l.getLectureName().equals("Lecture 1")));
    }

    @Test
    void findAll() {
        dao.saveLecture(lecture1);
        dao.saveLecture(lecture2);
        Collection<LectureImpl> allLectures = dao.findAll();
        assertEquals(2, allLectures.size());
    }

    @Test
    void deleteLecture() {
        dao.saveLecture(lecture1);
        boolean result = dao.deleteLecture(lecture1);
        assertTrue(result);

        LectureImpl deletedLecture = dao.findById(lecture1.getId());
        assertNull(deletedLecture);
    }
}
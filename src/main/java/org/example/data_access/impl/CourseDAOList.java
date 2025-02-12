package org.example.data_access.impl;

import org.example.data_access.CourseDAO;
import org.example.model.CourseImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CourseDAOList implements CourseDAO {
    private static Set<CourseImpl> courses = new HashSet<>();

    @Override
    public CourseImpl saveCourse(CourseImpl course) {
        if (course == null)
            throw new IllegalArgumentException("Course can't be null");

        CourseImpl existingCourse = findById(course.getId());
        if (existingCourse == null) {
            courses.add(course);
            return course;
        }

        courses.remove(existingCourse);

        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setSupervisor(course.getSupervisor());
        existingCourse.setLectures(course.getLectures());
        existingCourse.setStudents(course.getStudents());
        existingCourse.setStartDate(course.getStartDate());
        existingCourse.setWeekDuration(course.getWeekDuration());

        courses.add(existingCourse);

        return existingCourse;
    }

    @Override
    public CourseImpl findById(int id) {
        return courses.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CourseImpl> findByName(String name) {
        return courses.stream()
                .filter(c -> Objects.equals(c.getCourseName(), name))
                .toList();
    }

    @Override
    public List<CourseImpl> findByDate(LocalDate date) {
        return courses.stream()
                .filter(c -> c.getStartDate() == date)
                .toList();
    }

    @Override
    public List<CourseImpl> findAll() {
        return Set.copyOf(courses);
    }

    @Override
    public boolean removeCourse(CourseImpl course) {
        if (course == null) {
            throw new IllegalArgumentException("Course can't be null");
        }
        if (!courses.contains(course)) {
            throw new IllegalStateException("Course not found in the list");
        }

        courses.remove(course);
        return true;
    }
}

package org.example.data_access.impl;

import org.example.data_access.CourseDAO;
import org.example.model.CourseImpl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CourseDAOSet implements CourseDAO {
    private Set<CourseImpl> courses = new HashSet<>();
    private static volatile CourseDAOSet instance;

    public static CourseDAOSet getInstance() {
        if (instance == null) {
            synchronized (CourseDAOSet.class) {
                if (instance == null)
                    instance = new CourseDAOSet();
            }
        }
        return instance;
    }

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
    public Collection<CourseImpl> findByName(String name) {
        return courses.stream()
                .filter(c -> c.getCourseName().contains(name))
                .toList();
    }

    @Override
    public Collection<CourseImpl> findByDate(LocalDate date) {
        return courses.stream()
                .filter(c -> c.getStartDate() == date)
                .toList();
    }

    @Override
    public Collection<CourseImpl> findAll() {
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

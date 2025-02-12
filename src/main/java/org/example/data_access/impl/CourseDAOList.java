package org.example.data_access.impl;

import org.example.data_access.CourseDAO;
import org.example.model.Course;

import java.time.LocalDate;
import java.util.List;

public class CourseDAOList implements CourseDAO {
    @Override
    public Course saveCourse(Course course) {
        return null;
    }

    @Override
    public Course findById(int id) {
        return null;
    }

    @Override
    public List<Course> findByName(String name) {
        return List.of();
    }

    @Override
    public List<Course> findByDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<Course> findAll() {
        return List.of();
    }

    @Override
    public boolean removeCourse(Course course) {
        return false;
    }
}

package org.example.data_access.impl;

import org.example.data_access.CourseDAO;
import org.example.model.CourseImpl;

import java.time.LocalDate;
import java.util.List;

public class CourseDAOList implements CourseDAO {
    @Override
    public CourseImpl saveCourse(CourseImpl course) {
        return null;
    }

    @Override
    public CourseImpl findById(int id) {
        return null;
    }

    @Override
    public List<CourseImpl> findByName(String name) {
        return List.of();
    }

    @Override
    public List<CourseImpl> findByDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<CourseImpl> findAll() {
        return List.of();
    }

    @Override
    public boolean removeCourse(CourseImpl course) {
        return false;
    }
}

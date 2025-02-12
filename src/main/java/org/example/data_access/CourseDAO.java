package org.example.data_access;

import org.example.model.Course;

import java.util.*;
import java.time.LocalDate;

public interface CourseDAO {
    Course saveCourse(Course course);
    Course findById(int id);
    List<Course> findByName(String name);
    List<Course> findByDate(LocalDate date);
    List<Course> findAll();
    boolean removeCourse(Course course);
}

package org.example.data_access;

import org.example.model.CourseImpl;

import java.util.*;
import java.time.LocalDate;

public interface CourseDAO {
    CourseImpl saveCourse(CourseImpl course);
    
    CourseImpl findById(int id);
    List<CourseImpl> findByName(String name);
    List<CourseImpl> findByDate(LocalDate date);
    List<CourseImpl> findAll();
    boolean removeCourse(CourseImpl course);
}

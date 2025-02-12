package org.example.data_access;

import org.example.model.CourseImpl;

import java.util.*;
import java.time.LocalDate;

public interface CourseDAO {
    CourseImpl saveCourse(CourseImpl course);
    
    CourseImpl findById(int id);
    Collection<CourseImpl> findByName(String name);
    Collection<CourseImpl> findByDate(LocalDate date);
    Collection<CourseImpl> findAll();
    boolean removeCourse(CourseImpl course);
}

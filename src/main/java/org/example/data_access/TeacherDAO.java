package org.example.data_access;

import org.example.model.Course;
import org.example.model.Teacher;

import java.util.List;

public interface TeacherDAO {
    Teacher saveTeacher(Teacher teacher);
    Teacher findById(int id);
    Teacher findByEmail(String email);
    List<Teacher> findByName(String name);
    List<Teacher> findByCourse(Course course);
    boolean deleteTeacher(Teacher teacher);

}

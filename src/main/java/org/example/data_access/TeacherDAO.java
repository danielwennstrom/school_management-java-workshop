package org.example.data_access;

import org.example.model.CourseImpl;
import org.example.model.TeacherImpl;

import java.util.List;

public interface TeacherDAO {
    TeacherImpl saveTeacher(TeacherImpl teacher);
    TeacherImpl findById(int id);
    TeacherImpl findByEmail(String email);
    List<TeacherImpl> findByName(String name);
    List<TeacherImpl> findByCourse(CourseImpl course);
    boolean deleteTeacher(TeacherImpl teacher);

}

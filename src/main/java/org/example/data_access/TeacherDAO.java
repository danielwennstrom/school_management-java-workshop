package org.example.data_access;

import org.example.model.CourseImpl;
import org.example.model.TeacherImpl;

import java.util.Collection;

public interface TeacherDAO {
    TeacherImpl saveTeacher(TeacherImpl teacher);
    TeacherImpl findById(int id);
    TeacherImpl findByEmail(String email);
    Collection<TeacherImpl> findByName(String name);
    TeacherImpl findTeacherByCourse(CourseImpl course);
    boolean deleteTeacher(TeacherImpl teacher);

}

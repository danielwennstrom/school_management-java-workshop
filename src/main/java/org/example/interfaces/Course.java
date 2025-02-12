package org.example.interfaces;

import org.example.model.CourseImpl;
import org.example.model.StudentImpl;
import org.example.model.TeacherImpl;

public interface Course {
    CourseImpl registerTeacher(TeacherImpl teacher);
    CourseImpl registerStudent(StudentImpl student);
}

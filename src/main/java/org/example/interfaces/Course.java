package org.example.interfaces;

import org.example.model.StudentImpl;
import org.example.model.TeacherImpl;

public interface Course {
    Course registerTeacher(TeacherImpl teacher);
    Course registerStudent(StudentImpl student);
}

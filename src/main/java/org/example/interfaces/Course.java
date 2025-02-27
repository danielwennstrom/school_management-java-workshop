package org.example.interfaces;

import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.StudentImpl;
import org.example.model.TeacherImpl;

public interface Course {
    CourseImpl registerTeacher(TeacherImpl teacher);
    CourseImpl unregisterTeacher();
    CourseImpl registerStudent(StudentImpl student);
    CourseImpl unregisterStudent(StudentImpl student);
    CourseImpl registerLecture(LectureImpl lecture);
    CourseImpl unregisterLecture(LectureImpl lecture);
    String shortToString();
}

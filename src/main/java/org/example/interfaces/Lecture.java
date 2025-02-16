package org.example.interfaces;

import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;

public interface Lecture {
    LectureImpl registerTeacher(TeacherImpl teacher);
    LectureImpl unregisterTeacher(TeacherImpl teacher);
    CourseImpl registerCourse(CourseImpl course);
    CourseImpl unregisterCourse();
    String shortToString();
    String shortTeachersString();
}

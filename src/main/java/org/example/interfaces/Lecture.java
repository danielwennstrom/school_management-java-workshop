package org.example.interfaces;

import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;

public interface Lecture {
    LectureImpl registerTeacher(TeacherImpl teacher);
    LectureImpl unregisterTeacher(TeacherImpl teacher);
    String shortToString();
    String shortTeachersString();
}

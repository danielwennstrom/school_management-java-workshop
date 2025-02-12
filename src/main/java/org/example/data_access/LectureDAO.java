package org.example.data_access;

import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;

import java.util.List;

public interface LectureDAO {
    LectureImpl saveLecture(LectureImpl lecture);
    LectureImpl findById(int id);
    List<LectureImpl> findByTeacher(TeacherImpl teacher);
    List<LectureImpl> findByCourse(CourseImpl course);
    List<LectureImpl> findAll();
    boolean deleteLecture(LectureImpl lecture);
}

package org.example.data_access;

import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;

import java.util.Collection;

public interface LectureDAO {
    LectureImpl saveLecture(LectureImpl lecture);
    LectureImpl findById(int id);
    Collection<LectureImpl> findByTeacher(TeacherImpl teacher);
    Collection<LectureImpl> findByCourse(CourseImpl course);
    Collection<LectureImpl> findAll();
    boolean deleteLecture(LectureImpl lecture);
}

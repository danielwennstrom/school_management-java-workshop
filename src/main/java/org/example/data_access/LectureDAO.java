package org.example.data_access;

import org.example.model.Course;
import org.example.model.Lecture;
import org.example.model.Teacher;

import java.util.List;

public interface LectureDAO {
    Lecture saveLecture(Lecture lecture);
    void assignTeacherToLecture(Teacher teacher);
    Lecture findById(int id);
    List<Lecture> findByTeacher(Teacher teacher);
    List<Lecture> findByCourse(Course course);
    List<Lecture> findAll();
    boolean deleteLecture(Lecture lecture);
}

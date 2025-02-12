package org.example.data_access.impl;

import org.example.data_access.LectureDAO;
import org.example.model.Course;
import org.example.model.Lecture;
import org.example.model.Teacher;

import java.util.List;

public class LectureDAOList implements LectureDAO {
    @Override
    public Lecture saveLecture(Lecture lecture) {
        return null;
    }

    @Override
    public void assignTeacherToLecture(Teacher teacher) {

    }

    @Override
    public Lecture findById(int id) {
        return null;
    }

    @Override
    public List<Lecture> findByTeacher(Teacher teacher) {
        return List.of();
    }

    @Override
    public List<Lecture> findByCourse(Course course) {
        return List.of();
    }

    @Override
    public List<Lecture> findAll() {
        return List.of();
    }

    @Override
    public boolean deleteLecture(Lecture lecture) {
        return false;
    }
}

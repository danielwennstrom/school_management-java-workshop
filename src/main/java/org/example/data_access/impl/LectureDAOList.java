package org.example.data_access.impl;

import org.example.data_access.LectureDAO;
import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LectureDAOList implements LectureDAO {
    private static Set<LectureImpl> lectures = new HashSet<>();

    @Override
    public LectureImpl saveLecture(LectureImpl lecture) {
        return null;
    }

    @Override
    public LectureImpl findById(int id) {
        return null;
    }

    @Override
    public List<LectureImpl> findByTeacher(TeacherImpl teacher) {
        return List.of();
    }

    @Override
    public List<LectureImpl> findByCourse(CourseImpl course) {
        return List.of();
    }

    @Override
    public List<LectureImpl> findAll() {
        return List.of();
    }

    @Override
    public boolean deleteLecture(LectureImpl lecture) {
        return false;
    }
}

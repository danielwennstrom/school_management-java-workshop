package org.example.data_access.impl;

import org.example.data_access.LectureDAO;
import org.example.model.CourseImpl;
import org.example.model.LectureImpl;
import org.example.model.TeacherImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LectureDAOSet implements LectureDAO {
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
    public Collection<LectureImpl> findByTeacher(TeacherImpl teacher) {
        return List.of();
    }

    @Override
    public Collection<LectureImpl> findByCourse(CourseImpl course) {
        return List.of();
    }

    @Override
    public Collection<LectureImpl> findAll() {
        return List.of();
    }

    @Override
    public boolean deleteLecture(LectureImpl lecture) {
        return false;
    }
}

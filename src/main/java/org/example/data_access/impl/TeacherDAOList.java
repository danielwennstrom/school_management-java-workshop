package org.example.data_access.impl;

import org.example.data_access.TeacherDAO;
import org.example.model.CourseImpl;
import org.example.model.TeacherImpl;

import java.util.List;

public class TeacherDAOList implements TeacherDAO {
    @Override
    public TeacherImpl saveTeacher(TeacherImpl teacher) {
        return null;
    }

    @Override
    public TeacherImpl findById(int id) {
        return null;
    }

    @Override
    public TeacherImpl findByEmail(String email) {
        return null;
    }

    @Override
    public List<TeacherImpl> findByName(String name) {
        return List.of();
    }

    @Override
    public List<TeacherImpl> findByCourse(CourseImpl course) {
        return List.of();
    }

    @Override
    public boolean deleteTeacher(TeacherImpl teacher) {
        return false;
    }
}

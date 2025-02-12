package org.example.data_access.impl;

import org.example.data_access.TeacherDAO;
import org.example.model.CourseImpl;
import org.example.model.TeacherImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeacherDAOSet implements TeacherDAO {
    private static Set<TeacherImpl> teachers = new HashSet<>();

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
    public Collection<TeacherImpl> findByName(String name) {
        return List.of();
    }

    @Override
    public Collection<TeacherImpl> findByCourse(CourseImpl course) {
        return List.of();
    }

    @Override
    public boolean deleteTeacher(TeacherImpl teacher) {
        return false;
    }
}

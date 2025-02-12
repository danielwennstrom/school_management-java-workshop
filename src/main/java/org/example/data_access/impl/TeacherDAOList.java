package org.example.data_access.impl;

import org.example.data_access.TeacherDAO;
import org.example.model.Course;
import org.example.model.Teacher;

import java.util.List;

public class TeacherDAOList implements TeacherDAO {
    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public Teacher findById(int id) {
        return null;
    }

    @Override
    public Teacher findByEmail(String email) {
        return null;
    }

    @Override
    public List<Teacher> findByName(String name) {
        return List.of();
    }

    @Override
    public List<Teacher> findByCourse(Course course) {
        return List.of();
    }

    @Override
    public boolean deleteTeacher(Teacher teacher) {
        return false;
    }
}

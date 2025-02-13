package org.example.data_access.impl;

import org.example.data_access.TeacherDAO;
import org.example.model.CourseImpl;
import org.example.model.StudentImpl;
import org.example.model.TeacherImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeacherDAOSet implements TeacherDAO {
    private Set<TeacherImpl> teachers = new HashSet<>();
    private static volatile TeacherDAOSet instance;

    public static TeacherDAOSet getInstance() {
        if (instance == null) {
            synchronized (TeacherDAOSet.class) {
                if (instance == null)
                    instance = new TeacherDAOSet();
            }
        }
        return instance;
    }

    @Override
    public TeacherImpl saveTeacher(TeacherImpl teacher) {
        if (teacher == null)
            throw new IllegalArgumentException("Teacher can't be null");

        TeacherImpl existingTeacher = findById(teacher.getId());
        if (existingTeacher == null) {
            teachers.add(teacher);
            return teacher;
        }

        teachers.remove(existingTeacher);

        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());
        existingTeacher.setAddress(teacher.getAddress());

        teachers.add(existingTeacher);

        return existingTeacher;
    }

    @Override
    public TeacherImpl findById(int id) {
        return teachers.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public TeacherImpl findByEmail(String email) {
        return teachers.stream()
                .filter(t -> t.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<TeacherImpl> findByName(String name) {
        return teachers.stream()
                .filter(t -> t.getName().equals(name))
                .toList();
    }

    @Override
    public TeacherImpl findTeacherByCourse(CourseImpl course) {
        Collection<CourseImpl> courses = CourseDAOSet.getInstance().findAll();

        return courses.stream().filter(c -> c.getId() == course.getId())
                .map(CourseImpl::getSupervisor)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<TeacherImpl> findAll() {
        return Set.copyOf(teachers);
    }

    @Override
    public boolean deleteTeacher(TeacherImpl teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Lecture can't be null");
        }
        if (!teachers.contains(teacher)) {
            throw new IllegalStateException("Lecture not found in the list");
        }

        teachers.remove(teacher);
        return true;
    }
}

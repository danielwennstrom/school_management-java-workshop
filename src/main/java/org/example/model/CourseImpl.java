package org.example.model;

import org.example.interfaces.Course;

import java.time.LocalDate;
import java.util.List;

public class CourseImpl implements Course {
    private int id;
    private String courseName;
    private TeacherImpl supervisor;
    private LocalDate startDate;
    private int weekDuration;
    private List<StudentImpl> students;
    private List<LectureImpl> lectures;

    @Override
    public Course registerTeacher(TeacherImpl teacher) {
        return null;
    }

    @Override
    public Course registerStudent(StudentImpl student) {
        return null;
    }
}

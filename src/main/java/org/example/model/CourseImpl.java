package org.example.model;

import org.example.interfaces.Course;
import org.example.sequencers.CourseIdSequencer;

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

    public CourseImpl(String courseName) {
        this.id = CourseIdSequencer.getInstance().nextId();
        this.courseName = courseName;
    }

    @Override
    public Course registerTeacher(TeacherImpl teacher) {
        return null;
    }

    @Override
    public Course registerStudent(StudentImpl student) {
        return null;
    }
}

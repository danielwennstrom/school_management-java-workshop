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
    public CourseImpl registerTeacher(TeacherImpl teacher) {
        this.supervisor = teacher;
        return this;
    }

    @Override
    public CourseImpl registerStudent(StudentImpl student) {
        this.students.add(student);
        student.registerCourse(this);
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public TeacherImpl getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(TeacherImpl supervisor) {
        this.supervisor = supervisor;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getWeekDuration() {
        return weekDuration;
    }

    public void setWeekDuration(int weekDuration) {
        this.weekDuration = weekDuration;
    }

    public List<StudentImpl> getStudents() {
        return students;
    }

    public void setStudents(List<StudentImpl> students) {
        this.students = students;
    }

    public List<LectureImpl> getLectures() {
        return lectures;
    }

    public void setLectures(List<LectureImpl> lectures) {
        this.lectures = lectures;
    }
}

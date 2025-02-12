package org.example.model;

import org.example.interfaces.Lecture;
import org.example.sequencers.LectureIdSequencer;

import java.time.LocalDate;
import java.util.List;

public class LectureImpl implements Lecture {
    private int id;
    private String lectureName;
    private LocalDate date;
    private List<TeacherImpl> teachers;

    public LectureImpl(String lectureName) {
        this.id = LectureIdSequencer.getInstance().nextId();
        this.lectureName = lectureName;
    }

    @Override
    public Lecture registerTeacher(TeacherImpl teacher) {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<TeacherImpl> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherImpl> teachers) {
        this.teachers = teachers;
    }
}

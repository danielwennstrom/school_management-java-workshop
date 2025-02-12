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
}

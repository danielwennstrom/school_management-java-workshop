package org.example.model;

import org.example.data_access.impl.LectureDAOSet;
import org.example.interfaces.Lecture;
import org.example.sequencers.LectureIdSequencer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LectureImpl implements Lecture {
    private int id;
    private String lectureName;
    private LocalDate date;
    private List<TeacherImpl> teachers;

    public LectureImpl(String lectureName) {
        this.id = LectureIdSequencer.getInstance().nextId();
        this.lectureName = lectureName;
        this.teachers = new ArrayList<>();
    }

    @Override
    public LectureImpl registerTeacher(TeacherImpl teacher) {
        if (!teachers.contains(teacher)) {
            this.teachers.add(teacher);
            return this;
        }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("   ");
        sb.append("Lecture Name: ").append(lectureName).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Lecturers: \n");

        if (teachers == null || teachers.isEmpty()) {
            sb.append("none");
            return sb.toString();
        }

        if (teachers.size() == 1) {
            sb.append(teachers.getFirst().shortToString());
        } else {
            for (int i = 0; i < teachers.size(); i++) {
                sb.append(teachers.get(i).shortToString());
                if (i != teachers.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        return sb.toString();
    }

    public String shortToString() {
        return String.format("(%s) %s", getId(), getLectureName());
    }

    public String shortTeachersString() {
        StringBuilder sb = new StringBuilder();
        if (teachers == null || teachers.isEmpty()) {
            sb.append("none");
            return sb.toString();
        }
        for (int i = 0; i < teachers.size(); i++) {
            sb.append(teachers.get(i).shortToString());
            if (i != teachers.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}

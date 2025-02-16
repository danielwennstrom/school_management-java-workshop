package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.interfaces.Course;
import org.example.sequencers.CourseIdSequencer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseImpl implements Course {
    private int id;
    private String courseName;
    private TeacherImpl supervisor;
    @JsonProperty("startDate")
    private LocalDate startDate;
    @JsonProperty("weekDuration")
    private int weekDuration;
    private List<StudentImpl> students;
    private List<LectureImpl> lectures;

    @JsonCreator
    public CourseImpl(@JsonProperty("courseName") String courseName,
                      @JsonProperty("startDate") LocalDate startDate,
                      @JsonProperty("weekDuration") int weekDuration) {
        this.id = CourseIdSequencer.getInstance().nextId();
        this.courseName = courseName;
        this.startDate = startDate;
        this.weekDuration = weekDuration;
        this.students = new ArrayList<>();
        this.lectures = new ArrayList<>();
    }

    @Override
    public CourseImpl registerTeacher(TeacherImpl teacher) {
        this.supervisor = teacher;

        return this;
    }

    @Override
    public CourseImpl unregisterTeacher() {
        this.supervisor = null;

        return this;
    }

    @Override
    public CourseImpl registerStudent(StudentImpl student) {
        if (!students.contains(student)) {
            this.students.add(student);
            student.registerCourse(this);

            return this;
        }

        return null;
    }

    @Override
    public CourseImpl unregisterStudent(StudentImpl student) {
        if (students.contains(student)) {
            this.students.remove(student);
            student.unregisterCourse(this);

            return this;
        }

        return null;
    }

    @Override
    public CourseImpl registerLecture(LectureImpl lecture) {
        if (!lectures.contains(lecture)) {
            this.lectures.add(lecture);

            return this;
        }

        return null;
    }

    @Override
    public CourseImpl unregisterLecture(LectureImpl lecture) {
        if (lectures.contains(lecture)) {
            this.lectures.remove(lecture);

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: ").append(id).append("    ").append("Course Name: ")
                .append(courseName).append("\n").append("Starting date: ").append(startDate).append("   ")
                .append("Week Duration: ").append(weekDuration).append("\n")
                .append("Supervisor: ");

        if (supervisor != null) {
            sb.append("(").append(supervisor.getId()).append(")").append(" ");
            sb.append(supervisor.getName());
        } else
            sb.append("none");

        sb.append("\n").append("Lectures: ").append("\n");

        for (int i = 0; i < lectures.size(); i += 2) {
            LectureImpl lecture1 = lectures.get(i);
            LectureImpl lecture2 = null;

            if (i + 1 < lectures.size()) {
                lecture2 = lectures.get(i + 1);
            }

            sb.append(lecture1.shortToString()).append("   ");
            if (lecture2 != null) {
                sb.append(lecture2.shortToString());
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    public String shortToString() {
        return String.format("(%s) %s", getId(), getCourseName());
    }
}

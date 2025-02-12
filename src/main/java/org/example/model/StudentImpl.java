package org.example.model;

import org.example.abstracts.AbstractPerson;
import org.example.data_access.impl.CourseDAOSet;

import java.util.ArrayList;
import java.util.List;

public class StudentImpl extends AbstractPerson {
    private List<CourseImpl> courses;

    public StudentImpl(String name, String email, String address) {
        super(name, email, address);
        this.courses = new ArrayList<>();
    }

    public CourseImpl registerCourse(CourseImpl course) {
        if (!courses.contains(course)) {
            courses.add(course);
            CourseDAOSet.getInstance().saveCourse(course);
            return course;
        }

        return null;
    }

    public List<CourseImpl> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseImpl> courses) {
        this.courses = courses;
    }
}

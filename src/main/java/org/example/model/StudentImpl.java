package org.example.model;

import org.example.abstracts.AbstractPerson;
import org.example.data_access.impl.CourseDAOSet;

import java.util.ArrayList;
import java.util.List;

public class StudentImpl extends AbstractPerson {
    private List<CourseImpl> courses;

    public StudentImpl() { setRole(Role.STUDENT);}

    public StudentImpl(String name, String email, String address) {
        super(name, email, address);
        setRole(Role.STUDENT);
        this.courses = new ArrayList<>();
    }

    public StudentImpl registerCourse(CourseImpl course) {
        if (!courses.contains(course)) {
            courses.add(course);
            return this;
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

package org.example.model;

import org.example.abstracts.AbstractPerson;
import org.example.interfaces.Course;

import java.util.List;

public class StudentImpl extends AbstractPerson {
    private List<Course> courses;

    public StudentImpl(String name, String email, String address) {
        super(name, email, address);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

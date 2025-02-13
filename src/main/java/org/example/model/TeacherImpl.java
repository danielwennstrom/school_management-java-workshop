package org.example.model;

import org.example.abstracts.AbstractPerson;

public class TeacherImpl extends AbstractPerson {
    public TeacherImpl() {}

    public TeacherImpl(String name, String email, String address)
    {
        super(name,email,address);
    }
}

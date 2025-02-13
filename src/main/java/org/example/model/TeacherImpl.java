package org.example.model;

import org.example.abstracts.AbstractPerson;

public class TeacherImpl extends AbstractPerson {
    public TeacherImpl() { setRole(Role.TEACHER); }

    public TeacherImpl(String name, String email, String address)
    {
        super(name,email,address);
        setRole(Role.TEACHER);
    }
}

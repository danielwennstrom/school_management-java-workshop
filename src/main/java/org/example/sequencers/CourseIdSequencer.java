package org.example.sequencers;

import org.example.interfaces.Sequencer;

public class CourseIdSequencer implements Sequencer {
    private int currentId = 0;
    private static volatile CourseIdSequencer instance;

    private CourseIdSequencer() {}

    public static CourseIdSequencer getInstance() {
        if (instance == null) {
            synchronized (CourseIdSequencer.class) {
                if (instance == null) {
                    instance = new CourseIdSequencer();
                }
            }
        }
        return instance;
    }

    @Override
    public int nextId() {
        return ++currentId;
    }

    @Override
    public int getCurrentId() {
        return this.currentId;
    }

    @Override
    public void setCurrentId(int id) {
        this.currentId = id;
    }

}

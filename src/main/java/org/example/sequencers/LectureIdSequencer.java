package org.example.sequencers;

import org.example.interfaces.Sequencer;

public class LectureIdSequencer implements Sequencer {
    private int currentId = 0;
    private static volatile LectureIdSequencer instance;

    private LectureIdSequencer() {}

    public static LectureIdSequencer getInstance() {
        if (instance == null) {
            synchronized (LectureIdSequencer.class) {
                if (instance == null) {
                    instance = new LectureIdSequencer();
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

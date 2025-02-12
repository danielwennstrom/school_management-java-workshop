package org.example.interfaces;

public interface Sequencer {
    int nextId();
    int getCurrentId();
    void setCurrentId(int id);
}

package org.example.sequencers;

import org.example.interfaces.Sequencer;

public class PersonIdSequencer implements Sequencer {
        private int currentId = 0;
        private static volatile PersonIdSequencer instance;

        private PersonIdSequencer() {}

        public static PersonIdSequencer getInstance() {
            if (instance == null) {
                synchronized (PersonIdSequencer.class) {
                    if (instance == null) {
                        instance = new PersonIdSequencer();
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

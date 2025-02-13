package org.example.interfaces;

import org.example.abstracts.AbstractPerson;

public interface SelectionHandler {
    boolean addSelection(AbstractPerson person);
    void removeSelection(AbstractPerson person);
    void clearSelection();
}

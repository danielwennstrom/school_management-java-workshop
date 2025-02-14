package org.example.interfaces;

import org.example.abstracts.AbstractPerson;
import org.example.abstracts.AbstractPerson.Role;

public interface SelectionHandler {
    boolean addSelection(AbstractPerson person);
    void removeSelection(AbstractPerson person);
    Role getSelectorRole();
    void clearSelections();
}

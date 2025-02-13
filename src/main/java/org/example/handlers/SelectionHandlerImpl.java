package org.example.handlers;

import org.example.abstracts.AbstractPerson;
import org.example.interfaces.SelectionHandler;

import java.util.ArrayList;
import java.util.List;

public class SelectionHandlerImpl implements SelectionHandler {
    private List<AbstractPerson> selections;

    public SelectionHandlerImpl() {
        this.selections = new ArrayList<>();
    }

    @Override
    public boolean addSelection(AbstractPerson person) {
        AbstractPerson first = selections.getFirst();

        if (person == null)
            throw new IllegalArgumentException("Person can't be null.");

        if (first != null && person.getRole() != first.getRole()) {
            System.out.println("Can't add a selection with different role.");
            return false;
        }

        selections.add(person);
        return true;
    }

    @Override
    public void removeSelection(AbstractPerson person) {
        if (person == null)
            throw new IllegalArgumentException("Person can't be null.");

        selections.remove(person);
    }

    @Override
    public void clearSelection() {
        selections.clear();
    }

    public List<AbstractPerson> getSelections() {
        return selections;
    }
}

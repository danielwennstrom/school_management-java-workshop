package org.example.handlers;

import org.example.abstracts.AbstractPerson;
import org.example.abstracts.AbstractPerson.Role;
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
        AbstractPerson first = selections.stream().findFirst().orElse(null);

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
    public Role getSelectorRole() {
        return selections.stream().findFirst().orElseThrow().getRole();
    }

    @Override
    public void clearSelections() {
        selections.clear();
    }

    public List<AbstractPerson> getSelections() {
        return selections;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (selections.isEmpty()) {
            sb.append("none");
        } else {
            for (int i = 0; i < selections.size(); i++) {
                AbstractPerson person = selections.get(i);
                sb.append("(").append(person.getId()).append(") ").append(person.getName());
                if (i != selections.size() - 1)
                    sb.append(", ");
            }
        }

        return sb.toString();
    }
}

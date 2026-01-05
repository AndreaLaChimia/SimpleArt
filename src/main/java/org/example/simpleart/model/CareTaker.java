package org.example.simpleart.model;

import java.util.Stack;

public class CareTaker {

    private final Stack<Memento> stack = new Stack<>();


    public void save(Memento m) {
        stack.push(m);
    }

    public Memento undo() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.pop();
    }

    public boolean hasUndo() {
        return !stack.isEmpty();
    }

    public void clear() {
        stack.clear();
    }

}
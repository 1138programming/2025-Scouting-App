package com.scouting_app_2025.UIElements;

import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

public class UndoStack {
    private final Stack<Integer> inputStack = new Stack<Integer>();
    private final Stack<String> timestamps = new Stack<String>();
    private final Stack<Integer> redoStack = new Stack<Integer>();
    private final Stack<String> redoTimestamps = new Stack<String>();
    private final HashMap<Integer, Element> allElements = new HashMap<Integer, Element>();
    private final HashMap<Element, Integer> reverseElements = new HashMap<Element, Integer>();

    public UndoStack() {

    }

    public void addButton(Button button) {
        allElements.put(allElements.size(), button);
        reverseElements.put(button, reverseElements.size());
    }

    /**
     * @Info: Java is stupid and does not know when {@code allElements.get()}
     * is null, so it has to be checked.
     */
    public void undo() {
        if(inputStack.isEmpty()) return;
        redoStack.push(inputStack.pop());
        redoTimestamps.push(timestamps.pop());
        Objects.requireNonNull(allElements.get(redoStack.peek())).undo();
    }
    public void redo() {
        if(redoStack.isEmpty()) return;
        inputStack.push(redoStack.pop());
        timestamps.push(redoTimestamps.pop());
        Objects.requireNonNull(allElements.get(redoStack.peek())).redo();
    }
}

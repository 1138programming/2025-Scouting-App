package com.scouting_app_2025;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class UndoStack {
    private final Stack<Integer> inputStack = new Stack<Integer>();
    private final Stack<String> timestamps = new Stack<String>();
    private final Stack<Integer> redoStack = new Stack<Integer>();
    private final Stack<String> redoTimestamps = new Stack<String>();
    private HashMap<Integer, Object> allElements = new HashMap<Integer, Object>();
    private HashMap<Object, Integer> reverseElements = new HashMap<Object, Integer>();

    public UndoStack() {

    }

    public void addButton(Button button) {
        allElements.put(allElements.size(), button);
        reverseElements.put(button, reverseElements.size());
    }
    public void undo() {
        if(inputStack.isEmpty()) return;
        inputStack.push(redoStack.pop());
        timestamps.push(redoTimestamps.pop());

    }
    public void redo() {
        if(redoStack.isEmpty()) return;
        redoStack.push(inputStack.pop());
        redoTimestamps.push(timestamps.pop());

    }
}

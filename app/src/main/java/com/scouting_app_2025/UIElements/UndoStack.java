package com.scouting_app_2025.UIElements;

import android.content.Context;

import static com.scouting_app_2025.MainActivity.calendar;

import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

/**
 * @Info:
 */
public class UndoStack {
    private final Stack<Integer> inputStack = new Stack<Integer>();
    private final Stack<Long> timestamps = new Stack<Long>();
    private final Stack<Integer> redoStack = new Stack<Integer>();
    private final Stack<Long> redoTimestamps = new Stack<Long>();
    private final HashMap<Integer, UIElement> allElements = new HashMap<Integer, UIElement>();
    private final HashMap<UIElement, Integer> reverseElements = new HashMap<UIElement, Integer>();

    public UndoStack() {

    }

    public void addElement(UIElement element) {
        allElements.put(allElements.size(), element);
        reverseElements.put(element, reverseElements.size());
    }

    public void addTimestamp(UIElement element) {
        if(!reverseElements.containsKey(element)) {
            addElement(element);
        }
        inputStack.add(reverseElements.get(element));
        timestamps.add(calendar.getTimeInMillis());
    }
    /**
     * @Info:
     */
    public void undo() {
        if(inputStack.isEmpty()) return;
        redoStack.push(inputStack.pop());
        redoTimestamps.push(timestamps.pop());
        /* allElements.get() might be null, so it must be checked in this way
         * (even though this should never be the case due to how the stacks are stored)
        */
        Objects.requireNonNull(allElements.get(redoStack.peek())).undo();
    }
    public void redo() {
        if(redoStack.isEmpty()) return;
        inputStack.push(redoStack.pop());
        timestamps.push(redoTimestamps.pop());
        /* allElements.get() might be null, so it must be checked in this way
         * (even though this should never be the case due to how the stacks are stored)
         */
        Objects.requireNonNull(allElements.get(redoStack.peek())).redo();
    }
}

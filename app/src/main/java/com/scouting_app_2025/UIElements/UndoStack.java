package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.TAG;

import android.util.Log;

import com.scouting_app_2025.JSON.JSONManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;

/**
 * @Info:
 */
public class UndoStack {
    private final Stack<UIElement> inputStack = new Stack<UIElement>();
    private final Stack<Long> timestamps = new Stack<Long>();
    private Stack<UIElement> redoStack = new Stack<UIElement>();
    private final Stack<Long> redoTimestamps = new Stack<Long>();
    private final HashMap<Integer, UIElement> allElements = new HashMap<>();

    public UndoStack() {

    }

    public void addElement(UIElement element) {
        allElements.put(element.getID(), element);
    }

    public UIElement getElement(int datapointID) {
        return allElements.get(datapointID);
    }

    public void addTimestamp(UIElement element) {
        if(!allElements.containsKey(element.getID())) {
            addElement(element);
        }
        inputStack.add(element);
        timestamps.add(Calendar.getInstance(Locale.US).getTimeInMillis());
        redoStack = new Stack<>();
    }

    public JSONArray getTimestamps(JSONObject datapointTemplate) {
        JSONManager manager = new JSONManager(datapointTemplate);

        for(UIElement element : inputStack) {
            Log.d(TAG, String.valueOf(element.getID()));
        }

        //saves each timestamped datapoint to the JSON
        for(UIElement element : inputStack) {
            manager.addDatapoint(element.getID(), element.getValue(), timestamps.pop().toString());
        }

        //saves each non-timestamped datapoint to the JSON
        for(UIElement element : allElements.values()) {
            if(!(element instanceof Button)) {
                manager.addDatapoint(element.getID(), element.getValue());
            }
        }
        return manager.getJSON();
    }
    /**
     * @Info:
     */
    public void undo() {
        if(inputStack.isEmpty()) return;

        inputStack.peek().undo();

        redoStack.push(inputStack.pop());
        redoTimestamps.push(timestamps.pop());
    }
    public void redo() {
        if(redoStack.isEmpty()) return;
        redoStack.peek().redo();
        inputStack.push(redoStack.pop());
        timestamps.push(redoTimestamps.pop());
    }
}

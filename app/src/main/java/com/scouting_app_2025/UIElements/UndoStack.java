package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.TAG;
import static com.scouting_app_2025.MainActivity.calendar;
import static com.scouting_app_2025.UIElements.DatapointIDs.datapointIDs;

import android.util.Log;
import android.widget.Toast;

import com.scouting_app_2025.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

/**
 * @Info:
 */
public class UndoStack {
    private final Stack<Integer> inputStack = new Stack<Integer>();
    private final Stack<Long> timestamps = new Stack<Long>();
    private Stack<Integer> redoStack = new Stack<Integer>();
    private final Stack<Long> redoTimestamps = new Stack<Long>();
    private final HashMap<Integer, UIElement> allElements = new HashMap<Integer, UIElement>();

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
        inputStack.add(element.getID());
        timestamps.add(calendar.getTimeInMillis());
        redoStack = new Stack<>();
    }

    public JSONArray getTimestamps(JSONObject datapointTemplate) throws JSONException {
        JSONArray jsonArr = new JSONArray();
        JSONObject tempJson;

        for(int i : inputStack) {
            tempJson = datapointTemplate;
            tempJson.put("datapointID", Objects.requireNonNull(allElements.get(i)));
            tempJson.put("DCValue", Objects.requireNonNull(allElements.get(i)).getValue());
            tempJson.put("DCTimestamp", timestamps.pop());
            jsonArr.put(tempJson);
        }
        return jsonArr;
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
        if(allElements.get(redoStack.peek()) instanceof Button) {
            Objects.requireNonNull((Button)allElements.get(redoStack.peek())).undo(redoStack.peek());
        }
        else {
            Objects.requireNonNull(allElements.get(redoStack.peek())).undo();
        }
    }
    public void redo() {
        if(redoStack.isEmpty()) return;
        inputStack.push(redoStack.pop());
        timestamps.push(redoTimestamps.pop());
        /* allElements.get() might be null, so it must be checked in this way
         * (even though this should never be the case due to how the stacks are stored)
         */
        if(allElements.get(inputStack.peek()) instanceof Button) {
            Objects.requireNonNull((Button)allElements.get(inputStack.peek())).redo(inputStack.peek());
        }
        else {
            Objects.requireNonNull(allElements.get(inputStack.peek())).redo();
        }
    }
}

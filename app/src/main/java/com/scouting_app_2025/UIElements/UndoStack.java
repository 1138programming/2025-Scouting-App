package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.TAG;

import android.util.Log;

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
        timestamps.add(Calendar.getInstance(Locale.US).getTimeInMillis());
        redoStack = new Stack<>();
    }
    private JSONObject reconstructJSONObject(JSONObject a) throws JSONException {
        JSONObject newJSON = new JSONObject();
        newJSON.put("scouterID", a.get("scouterID"));
        newJSON.put("matchID", a.get("matchID"));
        newJSON.put("teamID", a.get("teamID"));
        newJSON.put("allianceID", a.get("allianceID"));

        return newJSON;
    }
    public JSONArray getTimestamps(JSONObject datapointTemplate) throws JSONException {
        JSONArray jsonArr = new JSONArray();
        JSONObject tempJson;
        for(Integer i : inputStack) {
            Log.d(TAG, i.toString());
        }
        for(int i : inputStack) {
            tempJson = reconstructJSONObject(datapointTemplate);
            tempJson.put("datapointID", Integer.toString(i));
            tempJson.put("DCValue", Objects.requireNonNull(allElements.get(i)).getValue());
            tempJson.put("DCTimestamp", timestamps.pop().toString());
            jsonArr.put(tempJson);
        }
        for(UIElement i : allElements.values()) {
            if(i instanceof Checkbox) {
                if(!((Checkbox) i).isChecked()) {
                    tempJson = reconstructJSONObject(datapointTemplate);
                    tempJson.put("datapointID", Integer.toString(i.getID()));
                    tempJson.put("DCValue", i.getValue());
                    tempJson.put("DCTimestamp", "0");
                    jsonArr.put(tempJson);
                }
            }
            else if(!(i instanceof Button)) {
                tempJson = reconstructJSONObject(datapointTemplate);
                tempJson.put("datapointID", Integer.toString(i.getID()));
                tempJson.put("DCValue", i.getValue());
                tempJson.put("DCTimestamp", "0");
                jsonArr.put(tempJson);
            }
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

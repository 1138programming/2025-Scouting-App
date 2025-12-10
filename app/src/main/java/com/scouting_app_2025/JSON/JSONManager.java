package com.scouting_app_2025.JSON;

import static com.scouting_app_2025.MainActivity.TAG;
import static com.scouting_app_2025.MainActivity.datapointEventValue;
import static com.scouting_app_2025.MainActivity.defaultTimestamp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONManager {
    private final JSONObject jsonTemplate;
    private final JSONArray masterJSON = new JSONArray();
    public JSONManager(JSONObject jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    public void addDatapoint(int datapointID, String value, String timestamp) {
        JSONObject temp;
        try {
            temp = new JSONObject(jsonTemplate.toString());
        } catch (JSONException e) {
            Log.wtf(TAG, "Something horrible has gone wrong when creating new template JSON");
            return;
        }

        try {
            temp.put("datapointID", datapointID);
            temp.put("DCValue", value);
            temp.put("DCTimestamp", timestamp);
        }
        catch (JSONException e) {
            Log.e(TAG, "Failed to add datapoint");
            return;
        }
        masterJSON.put(temp);
    }

    public void addDatapoint(int datapointID, String value) {
        addDatapoint(datapointID, value, defaultTimestamp);
    }

    public void addStart(int datapointID, String timestamp) {
        addDatapoint(datapointID, datapointEventValue, timestamp);
    }

    public JSONArray getJSON() {
        return masterJSON;
    }
}

package com.scouting_app_2025.JSON;

import static com.scouting_app_2025.MainActivity.TAG;
import static com.scouting_app_2025.MainActivity.datapointEventValue;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONManager {
    private JSONObject jsonTemplate;
    private JSONArray masterJSON;
    public JSONManager(JSONObject jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    public void setJSONTemplate(JSONObject jsonTemplate) {
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

    public JSONArray getMasterJSON() {
        return masterJSON;
    }
}

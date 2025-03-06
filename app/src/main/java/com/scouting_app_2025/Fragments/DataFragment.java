package com.scouting_app_2025.Fragments;

import androidx.fragment.app.Fragment;

import com.scouting_app_2025.UIElements.GUIManager;

import org.json.JSONArray;
import org.json.JSONException;

public class DataFragment extends Fragment {
    protected GUIManager guiManager = new GUIManager();

    public DataFragment() {

    }

    public JSONArray getFragmentMatchData() throws JSONException {
        return guiManager.getFragmentMatchData();
    }
}

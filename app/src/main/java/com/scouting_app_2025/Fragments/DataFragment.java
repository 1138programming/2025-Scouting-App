package com.scouting_app_2025.Fragments;

import androidx.fragment.app.Fragment;

import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.UIElements.UndoStack;

import org.json.JSONArray;
import org.json.JSONException;

public class DataFragment extends Fragment {
    protected UndoStack undoStack;

    public DataFragment() {
        undoStack = new UndoStack();
    }

    public JSONArray getFragmentMatchData() throws JSONException {
        return undoStack.getTimestamps(((MainActivity) MainActivity.context).getBaseJSON());

    }
}

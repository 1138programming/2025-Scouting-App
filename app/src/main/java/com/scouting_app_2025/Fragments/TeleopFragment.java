package com.scouting_app_2025.Fragments;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.scouting_app_2025.UIElements.GUIManager;

public class TeleopFragment extends Fragment {
    private final Context context;
    private final GUIManager guiManager;
    public TeleopFragment(Context context) {
        this.context = context;
        guiManager = new GUIManager(context);
        this.init();
    }

    private void init() {

    }
}

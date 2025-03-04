package com.scouting_app_2025.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.databinding.PostMatchFragmentBinding;

public class PostMatchFragment extends Fragment {
    PostMatchFragmentBinding binding;
    GUIManager guiManager = new GUIManager();

    public PostMatchFragment() {

    }


    @NonNull
    @Override
    public String toString() {
        return "PreAutonFragment";
    }
}

package com.scouting_app_2025.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.databinding.ArchiveFragmentBinding;

public class ArchiveFragment extends DataFragment {
    ArchiveFragmentBinding binding;
    GUIManager guiManager;

    public ArchiveFragment() {
        this.guiManager = super.guiManager;
    }

    @NonNull
    @Override
    public String toString() {
        return "ArchiveFragment";
    }
}

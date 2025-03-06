package com.scouting_app_2025.Fragments.Popups;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.Fragments.DataFragment;
import com.scouting_app_2025.UIElements.GUIManager;

public class ArchiveConfirm extends DataFragment {

    GUIManager guiManager;

    public ArchiveConfirm() {
        this.guiManager = super.guiManager;
    }

    @NonNull
    @Override
    public String toString() {
        return "ArchiveConfirmFragment";
    }
}

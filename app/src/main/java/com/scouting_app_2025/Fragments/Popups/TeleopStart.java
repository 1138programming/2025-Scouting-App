package com.scouting_app_2025.Fragments.Popups;

import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scouting_app_2025.Fragments.DataFragment;
import com.scouting_app_2025.Fragments.TeleopFragment;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.databinding.TeleopStartFragmentBinding;

import java.util.Objects;

public class TeleopStart extends DataFragment {
    TeleopStartFragmentBinding binding;
    GUIManager guiManager;

    public TeleopStart() {
        this.guiManager = super.guiManager;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = TeleopStartFragmentBinding.inflate(inflater,container,false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopStartBack)),
                binding.backButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopStartBack)), () ->
                ftm.teleopStartBack()
        );

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopStartStart)),
                binding.startButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopStartStart)), () ->
                ftm.teleopStartStart()
        );
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopStartStart)), () ->
                ((TeleopFragment) Objects.requireNonNull(getParentFragmentManager().findFragmentByTag("TeleopFragment"))).startTeleop()
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "TeleopStartFragment";
    }
}

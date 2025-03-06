package com.scouting_app_2025.Fragments.Popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scouting_app_2025.Fragments.AutonFragment;
import com.scouting_app_2025.Fragments.DataFragment;
import com.scouting_app_2025.UIElements.GUIManager;

import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.databinding.AutonStartFragmentBinding;

import java.util.Objects;

public class AutonStart extends DataFragment {
    AutonStartFragmentBinding binding;
    GUIManager guiManager;
    public AutonStart() {
        this.guiManager = super.guiManager;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = AutonStartFragmentBinding.inflate(inflater,container,false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonStartBack)),
                binding.backButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonStartBack)), () ->
            ftm.autonStartBack()
        );

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonStartStart)),
                binding.startButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonStartStart)), () ->
                ftm.autonStartStart()
        );
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonStartStart)), () ->
                ((AutonFragment) Objects.requireNonNull(getParentFragmentManager().findFragmentByTag("AutonFragment"))).startAuton()
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "AutonStartFragment";
    }
}

package com.scouting_app_2025.Fragments;

import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.R;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.databinding.TeleopFragmentBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TeleopFragment extends DataFragment {
    private TeleopFragmentBinding binding;
    private final GUIManager guiManager;
    private Long teleopStart;

    public TeleopFragment() {
        this.guiManager = super.guiManager;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = TeleopFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guiManager.createUndoRedoButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopUndo)),
                binding.undoButton,true);
        guiManager.createUndoRedoButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopRedo)),
                binding.redoButton,false);

        guiManager.createColorChanger(new ArrayList<>(List.of(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopScored)),Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonMissed)))),
                binding.swapScoredMissed,false, new ArrayList<>(List.of(MainActivity.context.getColor(R.color.dark_red))));

        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopScored)), new ArrayList<>(List.of(22,26)),
                binding.levelOneScored,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopScored)), new ArrayList<>(List.of(23,27)),
                binding.levelTwoScored,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopScored)), new ArrayList<>(List.of(24,28)),
                binding.levelThreeScored,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopScored)), new ArrayList<>(List.of(25,29)),
                binding.levelFourScored,true);

        guiManager.createButton(30, binding.coralPickup, true);

        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopScored)), new ArrayList<>(List.of(31,33)),
                binding.levelTwoAlgae,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopScored)), new ArrayList<>(List.of(32,34)),
                binding.levelThreeAlgae,true);

        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopScored)), new ArrayList<>(List.of(35,36)),
                binding.netButton,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopScored)), new ArrayList<>(List.of(37,38)),
                binding.processorButton,true);

        guiManager.createCheckbox(39, binding.hangAttemptedCheckbox, false, true);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopBack)),
                binding.backButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopBack)), () ->
                ftm.teleopBack()
        );

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopNext)),
                binding.nextButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeleopNext)), () ->
                ftm.teleopNext()
        );
    }

    public void teleopOpen() {
        if(teleopStart == null) {
            ftm.showTeleopStart();
        }
    }
    public void startTeleop() {
        this.teleopStart = Calendar.getInstance(Locale.US).getTimeInMillis();
    }
    public String getTeleopStart() {
        return this.teleopStart.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return "TeleopFragment";
    }
}

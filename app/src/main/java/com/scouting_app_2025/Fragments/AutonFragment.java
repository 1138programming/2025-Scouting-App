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
import com.scouting_app_2025.databinding.AutonFragmentBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AutonFragment extends DataFragment {
    AutonFragmentBinding binding;
    private final GUIManager guiManager;
    private Long autonStart;

    public AutonFragment() {
        this.guiManager = super.guiManager;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = AutonFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guiManager.createUndoRedoButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonUndo)),
                binding.undoButton,true);
        guiManager.createUndoRedoButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonRedo)),
                binding.redoButton,false);

        guiManager.createColorChanger(new ArrayList<>(List.of(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)),Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonMissed)))),
                binding.swapScoredMissed,false, new ArrayList<>(List.of(MainActivity.context.getColor(R.color.dark_red))));

        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)), new ArrayList<>(List.of(3,7)),
                binding.levelOneScored,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)), new ArrayList<>(List.of(4,8)),
                binding.levelTwoScored,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)), new ArrayList<>(List.of(5,9)),
                binding.levelThreeScored,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)), new ArrayList<>(List.of(6,10)),
                binding.levelFourScored,true);

        guiManager.createButton(11, binding.coralPickup, true);

        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)), new ArrayList<>(List.of(12,14)),
                binding.levelTwoAlgae,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)), new ArrayList<>(List.of(13,15)),
                binding.levelThreeAlgae,true);

        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)), new ArrayList<>(List.of(16,17)),
                binding.netButton,true);
        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)), new ArrayList<>(List.of(18,19)),
                binding.processorButton,true);

        guiManager.createCheckbox(20, binding.leaveQuestionCheckBox, false,true);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonBack)),
                binding.backButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonBack)), () ->
                ftm.autonBack()
        );

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonNext)),
                binding.nextButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonNext)), () ->
                ftm.autonNext()
        );
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonNext)), () ->
                ((TeleopFragment) Objects.requireNonNull(getParentFragmentManager().findFragmentByTag("TeleopFragment"))).teleopOpen()
        );
    }

    public void autonOpen() {
        if(autonStart == null) {
            ftm.showAutonStart();
        }
    }
    public void startAuton() {
        this.autonStart = Calendar.getInstance(Locale.US).getTimeInMillis();
    }

    public String getAutonStart() {
        return this.autonStart.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return "AutonFragment";
    }
}

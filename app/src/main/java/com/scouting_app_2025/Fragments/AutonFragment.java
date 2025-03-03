package com.scouting_app_2025.Fragments;

import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.R;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.databinding.AutonFragmentBinding;
import com.scouting_app_2025.databinding.PreAutonFragmentBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AutonFragment extends Fragment {
    AutonFragmentBinding binding;
    GUIManager guiManager = new GUIManager();

    public AutonFragment() {

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
                binding.swapScoredMissed,false, new ArrayList<>(List.of(Objects.requireNonNull(AppCompatResources.getDrawable(MainActivity.context, R.color.dark_red)))));

        guiManager.createColorChangerButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.AutonScored)), new ArrayList<>(List.of(3,7)),
                binding.levelOneScored,true);
    }
}

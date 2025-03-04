package com.scouting_app_2025.Fragments;

import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.databinding.PostMatchFragmentBinding;

import java.util.Objects;

public class PostMatchFragment extends Fragment {
    PostMatchFragmentBinding binding;
    GUIManager guiManager = new GUIManager();

    public PostMatchFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = PostMatchFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guiManager.createSpinner(40, binding.hangSpinner, false);

        guiManager.createCheckbox(41, binding.parkCheckbox, false);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PostMatchBack)), binding.returnToTeleop, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PostMatchBack)), () ->
                ftm.postMatchBack()
        );

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PostMatchSubmit)), binding.submitButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PostMatchSubmit)), () ->
            ftm.postMatchSubmit()
        );
    }

    private void openConfirmSubmit() {

    }

    @NonNull
    @Override
    public String toString() {
        return "PostMatchFragment";
    }
}

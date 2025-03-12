package com.scouting_app_2025.Fragments.Popups;

import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.Fragments.DataFragment;
import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.databinding.ConfirmSubmitFragmentBinding;

import java.util.Objects;

public class ConfirmSubmit extends DataFragment {
    ConfirmSubmitFragmentBinding binding;
    GUIManager guiManager;

    public ConfirmSubmit() {
        this.guiManager = super.guiManager;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = ConfirmSubmitFragmentBinding.inflate(inflater,container,false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ConfirmSubmitCancel)),
                binding.cancelButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ConfirmSubmitCancel)), () ->
                ftm.confirmSubmitBack()
        );

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ConfirmSubmitSubmit)),
                binding.submitButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ConfirmSubmitSubmit)), () -> {
            ((MainActivity)MainActivity.context).sendMatchData();
            ((MainActivity)MainActivity.context).recreateFragments();
        }
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "ConfirmSubmitFragment";
    }
}

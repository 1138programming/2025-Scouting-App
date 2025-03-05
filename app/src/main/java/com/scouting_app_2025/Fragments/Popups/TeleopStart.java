package com.scouting_app_2025.Popups;

import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.scouting_app_2025.Fragments.AutonFragment;
import com.scouting_app_2025.Fragments.TeleopFragment;
import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.R;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.databinding.TeleopStartFragmentBinding;

import java.util.Objects;

public class TeleopStart extends Fragment {
    TeleopStartFragmentBinding binding;
    GUIManager guiManager = new GUIManager();

    public TeleopStart() {

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
                ((TeleopFragment) Objects.requireNonNull(getParentFragmentManager().findFragmentByTag("TeleopFragment"))).teleopStart()
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "TeleopStartFragment";
    }
}

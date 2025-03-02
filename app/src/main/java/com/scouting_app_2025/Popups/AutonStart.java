package com.scouting_app_2025.Popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.scouting_app_2025.R;
import com.scouting_app_2025.UIElements.GUIManager;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;
import com.scouting_app_2025.databinding.AutonStartFragmentBinding;

import java.util.Objects;

public class AutonStart extends Fragment {
    AutonStartFragmentBinding binding;
    GUIManager guiManager = new GUIManager(getContext());
    public AutonStart() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = AutonStartFragmentBinding.inflate(inflater,container,false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get("Auton Start Back")),
                binding.backButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get("Auton Start Back")), () ->
            Navigation.findNavController(view).navigate(R.id.cancel)
        );

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get("Auton Start Start")),
                binding.startButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get("Auton Start Start")), () ->
                Navigation.findNavController(view).navigate(R.id.start)
        );
    }
}

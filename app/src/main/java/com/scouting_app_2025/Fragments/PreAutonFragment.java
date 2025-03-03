package com.scouting_app_2025.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.R;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.databinding.PreAutonFragmentBinding;

import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import java.util.Objects;

public class PreAutonFragment extends Fragment {
    PreAutonFragmentBinding binding;
    GUIManager guiManager = new GUIManager();

    public PreAutonFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = PreAutonFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guiManager.createTabletInfoSpinner(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ScouterName)), binding.nameOfScouterSpinner);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ScouterName)),
                () -> ((MainActivity)getContext()).updateTabletInformation());

        guiManager.createTabletInfoSpinner(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.MatchNumber)), binding.nameOfScouterSpinner);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.MatchNumber)),
                () -> ((MainActivity)getContext()).updateTabletInformation());

        guiManager.createTabletInfoSpinner(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamNumber)), binding.nameOfScouterSpinner);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamNumber)),
                () -> ((MainActivity)getContext()).updateTabletInformation());

        guiManager.createCheckbox(1, binding.noShowCheckbox, false);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext)),
                binding.nextButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext)), () ->
                Navigation.findNavController(view).navigate(R.id.next)
        );
//        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext)), () ->
//                Navigation.findNavController(view).navigate(R.id.popup)
//        );
    }

    public byte[] getTabletInformation() {
        return guiManager.getTabletInformation().getBytes();
    }

    public void setBtStatus(boolean status) {
        if(status) {
//            binding.btConnectionStatus.setText(getResources().getString(R.string.connected_status_title), TextView.BufferType.NORMAL);
            Toast.makeText(MainActivity.context, "connected", Toast.LENGTH_LONG).show();
        }
        else {
//            binding.btConnectionStatus.setText(getResources().getString(R.string.disconnected_status_title), TextView.BufferType.NORMAL);
            Toast.makeText(MainActivity.context, "disconnected", Toast.LENGTH_LONG).show();
        }
    }
}

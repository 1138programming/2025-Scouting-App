package com.scouting_app_2025.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.databinding.PreAutonFragmentBinding;

import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import java.util.ArrayList;
import java.util.Objects;

public class PreAutonFragment extends Fragment {
    PreAutonFragmentBinding binding;
    GUIManager guiManager = new GUIManager();
    ArrayList<Integer> scouterIDs = new ArrayList<>();

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

        guiManager.createTabletInfoSpinner(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ScouterName)), binding.nameOfScouterSpinner, true);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ScouterName)),
                () -> Objects.requireNonNull(((MainActivity)getContext())).updateTabletInformation());

        guiManager.createTabletInfoSpinner(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.MatchNumber)), binding.matchNumberSpinner, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.MatchNumber)),
                () -> Objects.requireNonNull(((MainActivity)getContext())).updateTabletInformation());
        guiManager.setSpinner(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.MatchNumber)), generateMatches(), true);

        guiManager.createTabletInfoSpinner(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamNumber)), binding.teamNumberSpinner, true);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamNumber)),
                () -> Objects.requireNonNull(((MainActivity)getContext())).updateTabletInformation());

        guiManager.createCheckbox(1, binding.noShowCheckbox, false);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext)),
                binding.nextButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext)), () ->
                ftm.preAutonNext()
        );
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext)), () ->
                ((AutonFragment) Objects.requireNonNull(getParentFragmentManager().findFragmentByTag("AutonFragment"))).autonOpen()
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "PreAutonFragment";
    }

    private ArrayList<CharSequence> generateMatches() {
        ArrayList<CharSequence> matchNumbers = new ArrayList<>();
        //creates spinner for match number
        for(int i = 1; i<=100; i++) {
            matchNumbers.add(Integer.toString(i));
        }
        for(int i = 1; i<=100; i++) {
            matchNumbers.add("Practice "+i);
        }
        for(int i = 1; i<=13; i++) {
            matchNumbers.add("Playoffs "+i);
        }
        for(int i = 1; i<=3; i++) {
            matchNumbers.add("Finals "+i);
        }
        return matchNumbers;
    }

    public byte[] getTabletInformation() {
        return guiManager.getTabletInformation().getBytes();
    }

    public void setScoutingInfo(ArrayList<ArrayList<CharSequence>> list) {
        for (CharSequence scouterNum : list.get(1)) {
            String curr = scouterNum.toString();
            this.scouterIDs.add(Integer.valueOf(curr));
        }
        guiManager.setTabletInfoElements(list);
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

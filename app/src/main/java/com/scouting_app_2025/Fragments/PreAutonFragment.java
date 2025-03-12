package com.scouting_app_2025.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.R;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.UIElements.Spinner;
import com.scouting_app_2025.databinding.PreAutonFragmentBinding;

import static com.scouting_app_2025.MainActivity.TAG;
import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PreAutonFragment extends DataFragment {
    PreAutonFragmentBinding binding;
    GUIManager guiManager;
    ArrayList<Integer> scouterIDs = new ArrayList<>(List.of(-1));

    public PreAutonFragment() {
        this.guiManager = super.guiManager;
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

        guiManager.createTabletInfoSpinner(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamNumber)), binding.teamNumberSpinner, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamNumber)),
                () -> Objects.requireNonNull(((MainActivity)getContext())).updateTabletInformation());

        guiManager.createRadioCheckboxGroup(1);
        guiManager.createCheckboxInGroup(1, Objects.requireNonNull(nonDataIDs.get(NonDataEnum.NoShow)),binding.noShowCheckbox, "NoShow");
        guiManager.createRadioGroupInGroup(1,Objects.requireNonNull(nonDataIDs.get(NonDataEnum.StartPosRadio)),binding.startingLocation);

        guiManager.createRadioGroup(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamColor)), binding.teamColorSwitch);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamColor)), this::setFieldImage);

        guiManager.createButton(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext)),
                binding.nextButton, false);
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext)), () ->
                ftm.preAutonNext()
        );
        guiManager.addAction(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext)), () ->
                ((AutonFragment) Objects.requireNonNull(getParentFragmentManager().findFragmentByTag("AutonFragment"))).autonOpen()
        );

//        ((MainActivity)MainActivity.context).updateBtScoutingInfo();
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
        for(int i = 1; i<=13; i++) {
            matchNumbers.add("Playoffs "+i);
        }
        for(int i = 1; i<=3; i++) {
            matchNumbers.add("Finals "+i);
        }
        for(int i = 1; i<=100; i++) {
            matchNumbers.add("Practice "+i);
        }
        return matchNumbers;
    }

    public byte[] getTabletInformation() {
        return guiManager.getTabletInformation().getBytes();
    }

    public void setScoutingInfo(ArrayList<ArrayList<CharSequence>> list) {
        this.scouterIDs = new ArrayList<>(List.of(this.scouterIDs.get(0)));
        for (CharSequence scouterNum : list.get(1)) {
            String curr = scouterNum.toString();
            this.scouterIDs.add(Integer.valueOf(curr));
        }
        guiManager.setTabletInfoElements(list);
    }

    public JSONObject getBaseJSON() throws JSONException {
        JSONObject baseJson = new JSONObject();

        baseJson.put("scouterID", scouterIDs.get(((Spinner)guiManager.getElement(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ScouterName)))).getSelectedIndex()).toString());
        baseJson.put("matchID", guiManager.getElement(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.MatchNumber))).getValue());
        try {
            baseJson.put("teamID", guiManager.getElement(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamNumber))).getValue());
        }
        catch(NullPointerException e) {
            baseJson.put("teamID", "0");
        }
        String allianceName = guiManager.getElement(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamColor))).getValue();
        switch(allianceName) {
            case "RED":
                baseJson.put("allianceID", "1");
                break;
            case "BLUE":
                baseJson.put("allianceID", "2");
        }

        return baseJson;
    }

    public String getFileTitle() {
        return (guiManager.getElement(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ScouterName)))).getValue()
                + " Match #"+(guiManager.getElement(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.MatchNumber)))).getValue();
    }

    public void setFieldImage() {
        switch(guiManager.getElement(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamColor))).getValue()) {
            case "RED":
                binding.startingPosImage.setImageResource(R.drawable.frc_field_red);
                break;
            case "BLUE":
                binding.startingPosImage.setImageResource(R.drawable.frc_field_blue);
        }
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

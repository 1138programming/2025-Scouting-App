package com.scouting_app_2025.Fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.R;
import com.scouting_app_2025.UIElements.Button;
import com.scouting_app_2025.UIElements.Checkbox;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.UIElements.NonDataEnum;
import com.scouting_app_2025.UIElements.RadioCheckboxGroup;
import com.scouting_app_2025.UIElements.RadioGroup;
import com.scouting_app_2025.UIElements.Spinner;
import com.scouting_app_2025.databinding.PreAutonFragmentBinding;

import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PreAutonFragmentRewrite extends Fragment {
    PreAutonFragmentBinding binding;

    Spinner scouterNameSpinner;
    Spinner matchNumberSpinner;
    RadioGroup teamColorButtons;
    Spinner teamNumberSpinner;
    RadioCheckboxGroup startingPositionGroup;
    RadioGroup startingPosition;
    Checkbox noShowCheckbox;
    Button<android.widget.Button> nextButton;

    public PreAutonFragmentRewrite() {}

    /* When the fragment binding is created we override the function so we
    * can get the binding in this class to use. */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = PreAutonFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int datapointID = Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ScouterName));
        scouterNameSpinner = new Spinner(datapointID, binding.nameOfScouterSpinner, true);
        scouterNameSpinner.setOnClickFunction(() -> ((MainActivity) requireContext()).updateTabletInformation());

        datapointID = Objects.requireNonNull(nonDataIDs.get(NonDataEnum.MatchNumber));
        matchNumberSpinner = new Spinner(datapointID, binding.matchNumberSpinner, false);
        matchNumberSpinner.updateSpinnerList(generateMatches());
        matchNumberSpinner.setOnClickFunction(() -> ((MainActivity) requireContext()).updateTabletInformation());

        datapointID = Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamColor));
        teamColorButtons = new RadioGroup(datapointID, binding.teamColorSwitch);
        teamColorButtons.setOnClickFunction(this::updateTeamColor);

        datapointID = Objects.requireNonNull(nonDataIDs.get(NonDataEnum.TeamNumber));
        teamNumberSpinner = new Spinner(datapointID, binding.teamNumberSpinner, false);
        teamNumberSpinner.setOnClickFunction(() -> ((MainActivity) requireContext()).updateTabletInformation());

        startingPositionGroup = new RadioCheckboxGroup(-1);
            datapointID = Objects.requireNonNull(nonDataIDs.get(NonDataEnum.StartPosRadio));
            startingPosition = new RadioGroup(datapointID, binding.startingLocation);
            startingPositionGroup.addElement(startingPosition);

            datapointID = Objects.requireNonNull(nonDataIDs.get(NonDataEnum.NoShow));
            noShowCheckbox = new Checkbox(datapointID, binding.noShowCheckbox, true, "noShow");
            startingPositionGroup.addElement(noShowCheckbox);

            startingPositionGroup.selectElement(noShowCheckbox);

        datapointID = Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext));
        nextButton = new Button<>(datapointID, binding.nextButton);
        nextButton.setOnClickFunction(() -> ftm.preAutonNext());
        nextButton.setOnClickFunction(() -> ((AutonFragment) Objects.requireNonNull(
                getParentFragmentManager().findFragmentByTag("AutonFragment"))).autonOpen());
    }

    /* When the fragment is completely created, we test so see
    * if we are connected and if so we send send our basic info. */
    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) MainActivity.context).updateBtScoutingInfo();
    }

    /* Makes it so the toString() function for this class
    * return the name of the class. */
    @NonNull
    @Override
    public String toString() {
        return "PreAutonFragment";
    }

    /** @Info: Generates an ArrayList filled with the necessary qualifying
     * and playoffs matches for an entire comp. */
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

    public void updateTeamColor() {
        switch(teamColorButtons.getValue()) {
            case "RED":
                binding.startingPosImage.setImageResource(R.drawable.frc_field_red);
                binding.startingLocation.setBackgroundTintList(ColorStateList.valueOf(
                        MainActivity.context.getColor(R.color.red)));
                break;
            case "BLUE":
                binding.startingPosImage.setImageResource(R.drawable.frc_field_blue);
                binding.startingLocation.setBackgroundTintList(ColorStateList.valueOf(
                        MainActivity.context.getColor(R.color.blue)));
        }
    }
}

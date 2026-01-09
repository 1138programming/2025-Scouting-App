package com.scouting_app_2025.Fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.scouting_app_2025.Bluetooth.CaptureAct;
import com.scouting_app_2025.Bluetooth.QrBtConnThread;
import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.R;
import com.scouting_app_2025.UIElements.Button;
import com.scouting_app_2025.UIElements.Checkbox;
import com.scouting_app_2025.DatapointIDs.NonDataEnum;
import com.scouting_app_2025.UIElements.RadioCheckboxGroup;
import com.scouting_app_2025.UIElements.RadioGroup;
import com.scouting_app_2025.UIElements.Spinner;
import com.scouting_app_2025.UIElements.ImageButton;
import com.scouting_app_2025.databinding.PreAutonFragmentBinding;

import static com.scouting_app_2025.MainActivity.ftm;
import static com.scouting_app_2025.DatapointIDs.DatapointIDs.nonDataIDs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PreAutonFragment extends DataFragment {
    private PreAutonFragmentBinding binding;
    private Spinner scouterNameSpinner;
    private Spinner matchNumberSpinner;
    private RadioGroup teamColorButtons;
    private Spinner teamNumberSpinner;

    private ArrayList<Integer> scouterIDs = new ArrayList<>(List.of(-1));

    public PreAutonFragment() {

    }

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

        RadioCheckboxGroup startingPositionGroup = new RadioCheckboxGroup(-1);
            datapointID = 1;
        RadioGroup startingPosition = new RadioGroup(datapointID, binding.startingLocation);
            startingPositionGroup.addElement(startingPosition);
            undoStack.addElement(startingPosition);

            datapointID = 2;
        Checkbox noShowCheckbox = new Checkbox(datapointID, binding.noShowCheckbox, true, "noShow");
            startingPositionGroup.addElement(noShowCheckbox);
            undoStack.addElement(noShowCheckbox);

            startingPositionGroup.selectElement(noShowCheckbox);

        datapointID = Objects.requireNonNull(nonDataIDs.get(NonDataEnum.PreAutonNext));
        Button nextButton = new Button(datapointID, binding.nextButton);
        nextButton.setOnClickFunction(() -> ftm.preAutonNext());
        nextButton.setOnClickFunction(() -> ((AutonFragment) Objects.requireNonNull(
                getParentFragmentManager().findFragmentByTag("AutonFragment"))).autonOpen());

        datapointID = Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ArchiveHamburger));
        ImageButton button = new ImageButton(datapointID, binding.archiveButton);
        button.setOnClickFunction(() -> ftm.preAutonMenu());
        button.setOnClickFunction(this::scanCode);
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

    public byte[] getTabletInformation() {
        StringBuilder tabletInfo = new StringBuilder();

        tabletInfo.append(scouterNameSpinner.getValue());
        tabletInfo.append(": ");
        tabletInfo.append(matchNumberSpinner.getValue());
        tabletInfo.append(": ");
        tabletInfo.append(teamNumberSpinner.getValue());
        tabletInfo.append(": ");

        if(tabletInfo.length() > 2) {
            return tabletInfo.delete(tabletInfo.length()-2,tabletInfo.length()).toString().getBytes();
        }
        else {
            return "Error".getBytes();
        }
    }

    public void setScoutingInfo(ArrayList<ArrayList<CharSequence>> list) {
        this.scouterIDs = new ArrayList<>(List.of(this.scouterIDs.get(0)));
        for (CharSequence scouterNum : list.get(1)) {
            String curr = scouterNum.toString();
            this.scouterIDs.add(Integer.valueOf(curr));
        }

        scouterNameSpinner.updateSpinnerList(list.get(0));
        teamNumberSpinner.updateSpinnerList(list.get(2));
    }

    public void setBtStatus(boolean status) {
        if(status) {
            binding.btConnectionStatus.setText(getResources().getString(R.string.connected_status_title), TextView.BufferType.NORMAL);
            Toast.makeText(MainActivity.context, "connected", Toast.LENGTH_LONG).show();
        }
        else {
            binding.btConnectionStatus.setText(getResources().getString(R.string.disconnected_status_title), TextView.BufferType.NORMAL);
            Toast.makeText(MainActivity.context, "disconnected", Toast.LENGTH_LONG).show();
        }
    }

    public String getFileTitle() {
        return scouterNameSpinner.getValue() + " Match #"+matchNumberSpinner.getValue();
    }

    public void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan QR-Code on Computer to Connect");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() == null) {
            Log.e("1138 SCApp", "QR-Code empty");
        }
        else {
            String contents = result.getContents();
            String[] results = contents.split(";");
            if(results.length == 2) {
                String log = "MAC Address: " + results[0] + "  Port: " + results[1];
                Log.d("1138 SCApp", log);
                QrBtConnThread.bluetoothConnect(results[0], Integer.parseInt(results[1]));
            }
            else {
                Log.e("1138 SCApp", "Error parsing QR-Code");
            }
        }
    });

    public JSONObject getBaseJSON() throws JSONException {
        JSONObject baseJson = new JSONObject();

        baseJson.put("scouterID", scouterIDs.get(scouterNameSpinner.getSelectedIndex()).toString());
        baseJson.put("matchID", matchNumberSpinner.getValue());
        try {
            baseJson.put("teamID", teamNumberSpinner.getValue());
        } catch (NullPointerException e) {
            baseJson.put("teamID", "0");
        }
        String allianceName = teamColorButtons.getValue();
        switch (allianceName) {
            case "RED":
                baseJson.put("allianceID", "1");
                break;
            case "BLUE":
                baseJson.put("allianceID", "2");
        }

        return baseJson;
    }
}

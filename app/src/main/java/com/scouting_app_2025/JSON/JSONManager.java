package com.scouting_app_2025.JSON;

import static com.scouting_app_2025.MainActivity.TAG;
import static com.scouting_app_2025.MainActivity.context;
import static com.scouting_app_2025.MainActivity.datapointEventValue;

import android.util.Log;
import android.widget.Toast;

import com.scouting_app_2025.Fragments.AutonFragment;
import com.scouting_app_2025.Fragments.DataFragment;
import com.scouting_app_2025.Fragments.PreAutonFragment;
import com.scouting_app_2025.Fragments.TeleopFragment;
import com.scouting_app_2025.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class JSONManager {
    private final HashMap<Fragments, DataFragment> fragments = new HashMap<>();
    public JSONManager(PreAutonFragment preAutonFragment) {
        fragments.put(Fragments.PreAutonFragment, preAutonFragment);
    }

    public void addFragment(Fragments fragType, DataFragment fragment) {
        fragments.put(fragType, fragment);
    }

    public void submitJSON() {
        JSONObject jsonFile = new JSONObject();
        JSONArray jsonArray;
        JSONArray jsonCollection = new JSONArray();
        try {
            for (DataFragment fragment : fragments.values()) {
                jsonArray = fragment.getFragmentMatchData();
                Log.d(TAG, jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonCollection.put(jsonArray.getJSONObject(i));
                }
            }
            //adding Auton and Teleop Start
            JSONObject temp =
                    ((PreAutonFragment) Objects.requireNonNull(fragments.get(Fragments.PreAutonFragment))).getBaseJSON();
            temp.put("datapointID", "2");
            temp.put("DCValue", datapointEventValue);
            temp.put("DCTimestamp",
                    ((AutonFragment) Objects.requireNonNull(fragments.get(Fragments.AutonFragment))).getAutonStart());
            jsonCollection.put(temp);

            temp = ((PreAutonFragment) Objects.requireNonNull(fragments.get(Fragments.PreAutonFragment))).getBaseJSON();
            temp.put("datapointID", "21");
            temp.put("DCValue", datapointEventValue);
            temp.put("DCTimestamp",
                    ((TeleopFragment) Objects.requireNonNull(fragments.get(Fragments.TeleopFragment))).getTeleopStart());
            jsonCollection.put(temp);

            jsonFile.put("scoutingData",jsonCollection);
        }
        catch (JSONException e) {
            Log.e(TAG, e.toString());
            return;
        }

        FileSaver.saveFile(jsonFile.toString(),
                ((PreAutonFragment) Objects.requireNonNull(fragments.get(Fragments.AutonFragment))).getFileTitle());

        ((MainActivity)context).submitMatchData(jsonFile.toString().getBytes(StandardCharsets.UTF_8));
    }
}

package com.scouting_app_2025;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.BLUETOOTH_ADVERTISE;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.scouting_app_2025.Bluetooth.BluetoothConnectedThread;
import com.scouting_app_2025.Bluetooth.BluetoothReceiver;
import com.scouting_app_2025.Fragments.ArchiveFragment;
import com.scouting_app_2025.Fragments.AutonFragment;
import com.scouting_app_2025.Fragments.DataFragment;
import com.scouting_app_2025.Fragments.FragmentTransManager;
import com.scouting_app_2025.Fragments.PostMatchFragment;
import com.scouting_app_2025.Fragments.PreAutonFragment;
import com.scouting_app_2025.Fragments.TeleopFragment;
import com.scouting_app_2025.JSON.FileSaver;
import com.scouting_app_2025.JSON.UpdateScoutingInfo;
import com.scouting_app_2025.Fragments.Popups.AutonStart;
import com.scouting_app_2025.Fragments.Popups.ConfirmSubmit;
import com.scouting_app_2025.Fragments.Popups.TeleopStart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Team1138ScoutingApp";
    public static final UUID MY_UUID = UUID.fromString("0007EA11-1138-1000-5465-616D31313338");
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public BluetoothConnectedThread connectedThread;
    public static FragmentTransManager ftm;
    public ArrayList<DataFragment> fragments = new ArrayList<>();
    public PreAutonFragment preAuton = new PreAutonFragment();
    public AutonStart autonStart = new AutonStart();
    public AutonFragment auton = new AutonFragment();
    public TeleopStart teleopStart = new TeleopStart();
    public TeleopFragment teleop = new TeleopFragment();
    public PostMatchFragment postMatch = new PostMatchFragment();
    public ConfirmSubmit confirmSubmit = new ConfirmSubmit();
    public ArchiveFragment archiveFragment = new ArchiveFragment();
    public PermissionManager permissionManager = new PermissionManager(this);
    public final static String datapointEventValue = "Event";
    private boolean connectivity = false;

    /**
     * @Info: Updates the variable that tracks Bluetooth Connectivity
     */
    public void setConnectivity(boolean connectivity) {
        this.connectivity = connectivity;
        updateConnectivity();
    }

    /**
     * @Info: called when GUI element needs to be updated. This is not a switch,
     * it looks at {@code connectivity} to make sure GUI element is accurate.
     */
    private void updateConnectivity() {
        runOnUiThread(() -> preAuton.setBtStatus(connectivity));
    }

    /**
     * @Info: Called once to instantiate {@code BluetoothReceiver()} and state
     * all the actions it should be listening for.
     */
    private void createReceiver() {
        BluetoothReceiver receiver = new BluetoothReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_UUID);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(receiver, filter);
    }

    private void addFragmentsToManager() {
        fragments.add(preAuton);
        fragments.add(auton);
        fragments.add(autonStart);
        fragments.add(teleop);
        fragments.add(teleopStart);
        fragments.add(postMatch);
        fragments.add(confirmSubmit);
        fragments.add(archiveFragment);

        ftm = new FragmentTransManager(fragments);
    }

    private void addPermissions() {
        permissionManager.addPermission(BLUETOOTH_CONNECT);
        permissionManager.addPermission(BLUETOOTH_SCAN);
        permissionManager.addPermission(ACCESS_FINE_LOCATION);
        permissionManager.addPermission(BLUETOOTH);
        permissionManager.addPermission(BLUETOOTH_ADMIN);
        permissionManager.addPermission(BLUETOOTH_ADVERTISE);
    }

    /**
     *
     */
    public void startScan() {
        BluetoothAdapter adapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        if (adapter == null) {
            Log.e(TAG, "no BT adapter");
        }
        if(adapter != null && permissionManager.checkPermission(BLUETOOTH_SCAN)) {
            adapter.cancelDiscovery();
            adapter.startDiscovery();
            Log.i(TAG, "should be receiving");
        }
    }

    /**
     * @Info: Called once at the start of the program to create the receivers
     * and begin discovery.
     */
    public void createReceiverScan() {
        createReceiver();
        startScan();
    }
    public void setConnectedThread(BluetoothConnectedThread connectedThread) {
        this.connectedThread = connectedThread;
    }
    public void updateTabletInformation() {
        if(!connectivity) return;
        byte[] info = preAuton.getTabletInformation();
        connectedThread.sendInformation(info, 2);
    }
    public void updateBtScoutingInfo() {
        if (!Objects.isNull(connectedThread) && !connectedThread.checkLists()) {
            connectedThread.updateLists();
        }
        preAuton.setScoutingInfo((new UpdateScoutingInfo()).getSplitFileData());
    }
    public JSONObject getBaseJSON() throws JSONException {
        return preAuton.getBaseJSON();
    }
    public void recreateFragments() {
        fragments.clear();
        preAuton = new PreAutonFragment();
        fragments.add(preAuton);
        auton = new AutonFragment();
        fragments.add(auton);
        autonStart = new AutonStart();
        fragments.add(autonStart);
        teleop = new TeleopFragment();
        fragments.add(teleop);
        teleopStart = new TeleopStart();
        fragments.add(teleopStart);
        postMatch = new PostMatchFragment();
        fragments.add(postMatch);
        confirmSubmit = new ConfirmSubmit();
        fragments.add(confirmSubmit);
        archiveFragment = new ArchiveFragment();
        fragments.add(archiveFragment);

        ftm = new FragmentTransManager(fragments);
    }
    public void sendMatchData() {
        JSONObject jsonFile = new JSONObject();
        JSONArray jsonArray;
        JSONArray jsonCollection = new JSONArray();
        try {
            for (DataFragment fragment : fragments) {
                jsonArray = fragment.getFragmentMatchData();
                Log.d(TAG, jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonCollection.put(jsonArray.getJSONObject(i));
                }
            }
            //adding Auton and Teleop Start
            JSONObject temp = getBaseJSON();
            temp.put("datapointID", "2");
            temp.put("DCValue", datapointEventValue);
            temp.put("DCTimestamp", auton.getAutonStart());
            jsonCollection.put(temp);

            temp = getBaseJSON();
            temp.put("datapointID", "21");
            temp.put("DCValue", datapointEventValue);
            temp.put("DCTimestamp", teleop.getTeleopStart());
            jsonCollection.put(temp);

            jsonFile.put("scoutingData",jsonCollection);
        }
        catch (JSONException e) {
            Log.e(TAG, e.toString());
            return;
        }

        FileSaver.saveFile(jsonFile.toString(), preAuton.getFileTitle());

        if(connectivity) {
            connectedThread.sendInformation(jsonFile.toString().getBytes(StandardCharsets.UTF_8), 1);
        }
        else {
            Toast.makeText(this, "Data has not been uploaded because bluetooth isn't connected", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * @Info: Called when tablets initially connect and is used
     * to easily detect when auton or teleop is incorrectly started.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        addFragmentsToManager();

        addPermissions();
        permissionManager.requestPermissions();
    }
}
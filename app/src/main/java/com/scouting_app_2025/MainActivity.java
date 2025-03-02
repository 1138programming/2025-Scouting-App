package com.scouting_app_2025;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.BLUETOOTH_ADVERTISE;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;

import com.scouting_app_2025.Bluetooth.BluetoothConnectedThread;
import com.scouting_app_2025.Bluetooth.BluetoothReceiver;
import com.scouting_app_2025.Fragments.AutonFragment;
import com.scouting_app_2025.Fragments.PostMatchFragment;
import com.scouting_app_2025.Fragments.PreAutonFragment;
import com.scouting_app_2025.Fragments.TeleopFragment;
import com.scouting_app_2025.Popups.AutonStart;
import com.scouting_app_2025.Popups.ConfirmSubmit;
import com.scouting_app_2025.Popups.TeleopStart;
import com.scouting_app_2025.UIElements.GUIManager;
import com.scouting_app_2025.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Team1138ScoutingApp";
    public static final UUID MY_UUID = UUID.fromString("0007EA11-1138-1000-5465-616D31313338");
    private ActivityMainBinding binding;
    public BluetoothConnectedThread connectedThread;
    public PreAutonFragment preAuton = new PreAutonFragment();
    public AutonStart autonStart = new AutonStart();
    public AutonFragment auton = new AutonFragment();
    public TeleopStart teleopStart = new TeleopStart();
    public TeleopFragment teleop = new TeleopFragment();
    public PostMatchFragment postMatch = new PostMatchFragment();
    public ConfirmSubmit confirmSubmit = new ConfirmSubmit();
    public PermissionManager permissionManager = new PermissionManager(this);
    public GUIManager guiManager = new GUIManager(this);
    public static Calendar calendar;
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
        preAuton.setBtStatus(connectivity);
    }

    /**
     * @Info: Called once to instantiate {@code BluetoothReceiver()} and state
     * all the actions it should be listening for.
     */
    private void createReceiver() {
        BluetoothReceiver receiver = new BluetoothReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_UUID);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(receiver, filter);
    }

    /**
     *
     */
    public void startScan() {
        BluetoothAdapter adapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        if (adapter == null) {
            Log.e(TAG, "no BT adapter");
        }
        if(permissionManager.checkPermission(BLUETOOTH_SCAN)) {
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
        byte[] info = preAuton.getTabletInformation();
        connectedThread.sendInformation(info, 2);
    }
    /**
     * @Info: Called when tablets initially connect and is used
     * to easily detect when auton or teleop is incorrectly started.
     */
    public void setTime(int year, int month, int date, int hour, int min, int sec) {
        calendar.clear();
        calendar.set(year,month,date,hour,min,sec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        permissionManager.addPermission(BLUETOOTH_CONNECT);
        permissionManager.addPermission(BLUETOOTH_SCAN);
        permissionManager.addPermission(ACCESS_FINE_LOCATION);
        permissionManager.addPermission(BLUETOOTH);
        permissionManager.addPermission(BLUETOOTH_ADMIN);
        permissionManager.addPermission(BLUETOOTH_ADVERTISE);

        permissionManager.requestPermissions();
        calendar.getTimeInMillis();

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.nav_graph);
        navController.setGraph(navGraph);
    }
}
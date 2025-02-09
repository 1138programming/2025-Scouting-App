package com.scouting_app_2025;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
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
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.scouting_app_2025.Bluetooth.BluetoothReceiver;
import com.scouting_app_2025.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Team1138ScoutingApp";
    public static final UUID MY_UUID = UUID.fromString("0007EA11-1138-1000-5465-616D31313338");
    private ActivityMainBinding binding;
    private BluetoothReceiver receiver;
    private BluetoothAdapter adapter;
    public PermissionManager permissionManager = new PermissionManager(this);
    private boolean connectivity = false;

    /**
     * @Info: Updates the variable that tracks Bluetooth Connectivity
     */
    public void setConnectivity(boolean connectivity) {
        this.connectivity = connectivity;
    }

    /**
     * @Info: called when GUI element needs to be updated. This is not a switch,
     * it looks at {@code connectivity} to make sure GUI element is accurate.
     */
    private void updateConnectivity() {
        //TODO: make function update GUI
    }

    /**
     * @Info: Called once to instantiate {@code BluetoothReceiver()} and state
     * all the actions it should be listening for.
     */
    private void createReceiver() {
        receiver = new BluetoothReceiver();
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
        adapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        if (adapter == null) {
            Log.e(TAG, "no BT adapter");
        }
        if(permissionManager.checkPermission(BLUETOOTH_SCAN)) {
            adapter.cancelDiscovery();
            adapter.startDiscovery();
            Log.i(TAG, "should be receiving");
        }
    }

    public void createReceiverScan() {
        createReceiver();
        startScan();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionManager.addPermission(BLUETOOTH_CONNECT);
        permissionManager.addPermission(BLUETOOTH_SCAN);
        permissionManager.addPermission(ACCESS_FINE_LOCATION);
        permissionManager.addPermission(BLUETOOTH);
        permissionManager.addPermission(BLUETOOTH_ADMIN);
        permissionManager.addPermission(BLUETOOTH_ADVERTISE);

        permissionManager.requestPermissions();
    }
}
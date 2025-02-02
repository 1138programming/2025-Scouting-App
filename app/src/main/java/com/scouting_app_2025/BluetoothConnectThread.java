package com.scouting_app_2025;

import static com.scouting_app_2025.MainActivity.TAG;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import androidx.annotation.RequiresPermission;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class BluetoothConnectThread {
//    private final Context context;
    private BluetoothSocket socket;
    private final BluetoothDevice device;
    private BluetoothAdapter adapter = null;
    private BluetoothConnectedThread connectedThread;

    public BluetoothConnectThread(BluetoothDevice device) {
        this.device = device;
    }

    private void init() {
        BluetoothSocket tmp = null;
//        try {
//
//        }
//        catch (IOException e) {
//            Log.e(TAG, e.toString());
//        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private boolean attemptCachedConnect() {
        Optional<BluetoothDevice> connectCandidate;
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        for(BluetoothDevice device : pairedDevices) {
            Log.i(TAG, "Local Device: ");
        }
        return true;
    }
}

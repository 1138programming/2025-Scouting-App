package com.scouting_app_2025;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;

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
        try {

        }
        catch (IOException e) {
            Log.e(MainActivity.TAG, e.toString());
        }
    }
}

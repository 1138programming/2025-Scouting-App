package com.scouting_app_2025.Bluetooth;

import static com.scouting_app_2025.MainActivity.TAG;
import static com.scouting_app_2025.MainActivity.MY_UUID;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.scouting_app_2025.MainActivity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class BluetoothReceiver extends BroadcastReceiver {
    public BluetoothReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        //receives action from intent
        String action = intent.getAction();
        BluetoothDevice device
                = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice.class);

        String deviceAddress;
        String deviceName;
        //device"Title" is what logs use to refer to the device that the action pertains to
        String deviceTitle = "";
        //EXTRA_DEVICE can be null, so checks if null
        if (device != null) {
            deviceAddress = device.getAddress();
            //device isn't guaranteed to have a name, so we need to check
            if (((MainActivity)MainActivity.context).permissionManager.checkPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                if(device.getName() != null) {
                    deviceName = device.getName();
                    deviceTitle = deviceAddress + " - (" + deviceName + ")";
                }
                else {
                    deviceTitle = deviceAddress;
                }
            }
        }
        //triggered in response to call to get UUIDs with SDP (called by ACTION_FOUND)
        if (BluetoothDevice.ACTION_UUID.equals(action)) {
            Log.i(TAG, "Checking UUID(s) from device: " + deviceTitle);

            //stores all received UUIDs
            Parcelable[] UUIDs = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
            if(UUIDs != null) {
                //cycles through all UUIDs provided
                for(Parcelable UUIDParcelable : UUIDs) {
                    String UUIDString = UUIDParcelable.toString();
                    Log.i(TAG, "UUID: " + UUIDString);

                    //connects to the device if the UUID is correct
                    if(UUID.fromString(UUIDString).equals(MY_UUID)) {
                        Log.i(TAG, "App Found!");

                        if(device != null) {
                            try {
                                BluetoothSocket socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                                socket.connect();
                                BluetoothConnectedThread connectedThread = new BluetoothConnectedThread(socket);
                                ((MainActivity)MainActivity.context).setConnectedThread(connectedThread);
                                ((MainActivity)MainActivity.context).setConnectivity(true);
                                ((MainActivity)MainActivity.context).updateBtScoutingInfo();
                                break;
                            } catch (IOException e) {
                                Log.e(TAG, "Failed to " + e);
                            }
                        }
                    }
                }
            }
        }

        //set of checks just used for the log
            else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.i(TAG, "Discovery Started");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.i(TAG, "Discovery Finished");
            }
            //Logs when a device is found
            //Format: "Device Found: [deviceAddress] - ([deviceName])"
            else if (BluetoothDevice.ACTION_FOUND.equals((action))) {
                Log.i(TAG, "Device Found: " + deviceTitle);
                if(device!= null && device.getName() != null) {
                    device.fetchUuidsWithSdp();
                }
            }
    }
}

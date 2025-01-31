package com.scouting_app_2025;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.UUID;

public class BluetoothReceiver extends BroadcastReceiver {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    BluetoothSocket socket;

    @Override
    public void onReceive(Context context, Intent intent) {
        //receives action from intent
        String action = intent.getAction();
        BluetoothDevice device = null;
        device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        String deviceAddress = "";
        String deviceName = "";
        //EXTRA_DEVICE can be null, so checks if null
        if(device != null) {
            //device isn't garunteed to have address and name, so
        }
    }
}

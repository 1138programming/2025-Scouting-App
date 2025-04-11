package com.scouting_app_2025.UniversalSerialBus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.scouting_app_2025.MainActivity;


public class UsbReceiver extends BroadcastReceiver {
    public static final String CONNECTION_ADDRESS_RECEIVED = "com.scouting_app_2025.UniversalSerialBus.MESSAGE_RECEIVED";
    public static final String PORT = "com.scouting_app_2025.UniversalSerialBus.PORT";
    public static final String MAC_ADDRESS = "com.scouting_app_2025.UniversalSerialBus.PORT";

    private UsbReadThread usbReadThread;
    public UsbReceiver() {
        this.usbReadThread = new UsbReadThread(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(UsbReceiver.CONNECTION_ADDRESS_RECEIVED.equals(action)) {
            synchronized(this) {
                //TODO: connect over bluetooth with info
            }
        }
    }

    public void startRead() {
        usbReadThread.start();
    }
}

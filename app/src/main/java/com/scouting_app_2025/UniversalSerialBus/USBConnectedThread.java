package com.scouting_app_2025.UniversalSerialBus;

import static com.scouting_app_2025.MainActivity.TAG;

import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.scouting_app_2025.MainActivity;

import java.util.HashMap;

public class USBConnectedThread {
    private final UsbManager usbManager;
    private UsbDeviceConnection usbDeviceConnection;
    private HashMap<String, UsbDevice> deviceList;
    private final String deviceName = "";
    private final UsbDevice connectedDevice;

    public USBConnectedThread() {
        usbManager = (UsbManager) (MainActivity.context).getSystemService(MainActivity.USB_SERVICE);
        deviceList = usbManager.getDeviceList();

        if(deviceList.containsKey(deviceName)){
            connectedDevice = deviceList.get(deviceName);
            usbDeviceConnection = usbManager.openDevice(connectedDevice);
        }
        else{
            Log.e(TAG, "Not Connected to Central Computer");
            connectedDevice = null;
        }
    }

    public void sendData() {
//        usbDeviceConnection.bulkTransfer()
//        UsbInterface usbInterface = connectedDevice.getInterface(); TODO: needs index?
//        usbInterface.getEndpoint(); TODO: can get endpoint from interface (but also needs index???)
    }
}

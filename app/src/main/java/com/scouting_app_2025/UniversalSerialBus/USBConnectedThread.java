package com.scouting_app_2025.UniversalSerialBus;

import static com.scouting_app_2025.MainActivity.TAG;

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
    private HashMap<UsbDevice, UsbEndpoint> endPoints;
    private HashMap<UsbDevice, UsbInterface> interfaces;
    private final String deviceName = "";
    private final UsbDevice connectedDevice;
    private final int USB_CLASS_HID = 3; //need to figure out -> probably 3
    private final int USB_ENDPOINT_GOAL = 0; //need to figure out -> i have no idea what it is

    public USBConnectedThread() {
        usbManager = (UsbManager) (MainActivity.context).getSystemService(MainActivity.USB_SERVICE);
        deviceList = usbManager.getDeviceList();

        for (UsbDevice device : deviceList.values()) {
            for (int i = 0; i < device.getInterfaceCount(); i++) {
                UsbInterface usbInterface = device.getInterface(i);
                if (usbInterface.getInterfaceClass() == USB_CLASS_HID) {
                    Log.i(TAG, "Interface found");
                    for (int j = 0; j < usbInterface.getEndpointCount(); j++) {
                        UsbEndpoint endpoint = usbInterface.getEndpoint(j);
                        if (endpoint.getAddress() == USB_ENDPOINT_GOAL) {
                            Log.i(TAG, "Endpoint found");
                            endPoints.put(device, endpoint);
                            interfaces.put(device, usbInterface);

                        }
                    }
                }
            }
        }


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
        byte[] bytes = {12, 13, 14, 15}; //test data
//        UsbInterface usbInterface = connectedDevice.getInterface(USB_CLASS_HID);
//        usbInterface.getEndpoint(USB_ENDPOINT_CLASS);
        for (UsbDevice device : deviceList.values()) {
            usbDeviceConnection.bulkTransfer(endPoints.get(device), bytes, bytes.length , 0);
            break;
        }
        //TODO: can get endpoint from interface (but also needs index???)
    }
}

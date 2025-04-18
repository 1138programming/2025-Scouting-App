package com.scouting_app_2025.UniversalSerialBus;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;

import static com.scouting_app_2025.MainActivity.TAG;

import com.scouting_app_2025.MainActivity;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class UsbReadThread extends Thread {
    private final UsbReceiver receiver;
    private final UsbManager usbManager;
    private UsbDevice device;
    private UsbInterface usbInterface;
    private UsbEndpoint readEndpoint;
    private UsbDeviceConnection deviceConnection;
    public UsbReadThread(UsbReceiver receiver) {
        this.receiver = receiver;
        this.usbManager = (UsbManager) MainActivity.context.getSystemService(Context.USB_SERVICE);

        Iterator<UsbDevice> it = usbManager.getDeviceList().values().iterator();
        if (it.hasNext()) {
            device = it.next();
        }
        else {
            Log.e(TAG, "No USB device detected");
            return;
        }

        this.deviceConnection = usbManager.openDevice(device);
        communicationSetup();
    }

    @Override
    public void run() {
        boolean continueReading = true;

        ByteBuffer buffer = ByteBuffer.allocate(255);
        UsbRequest request = new UsbRequest();
        request.initialize(deviceConnection, readEndpoint);

        String dataByte = "";
        StringBuilder data = new StringBuilder();
        int packetState = 0;

        Log.i(TAG, "Read listener starting");

        while(true) {
            request.queue(buffer);

            if(deviceConnection.requestWait() == request) {
                for(int i = 0; i < buffer.capacity() && buffer.get(i) != 0; i++) {
                    // transform ascii (0-255) to its character equivalent and append
                    dataByte = Character.toString((char) buffer.get(i));
                    if(packetState == 0 && dataByte.equals("["))
                    {
                        // start
                        packetState = 1;
                    }
                    else if(packetState == 1 && !dataByte.equals("]"))
                    {
                        // in-between
                        data.append(dataByte);
                    }
                    else if(packetState == 1 && dataByte.equals("]"))
                    {
                        // end
                        packetState = 2;
                        break;
                    }
                }

                if(packetState == 2)
                {
                    String[] dataArray = data.toString().split(";");

                    int port = Integer.parseInt(dataArray[0]);
                    Log.i(TAG, "Port: " + port);

                    String macAddress = dataArray[1];
                    Log.i(TAG, "Mac Address: " + macAddress);

                    Intent intent = new Intent();
                    intent.setAction(UsbReceiver.CONNECTION_ADDRESS_RECEIVED);
                    intent.putExtra(UsbReceiver.PORT, port);
                    intent.putExtra(UsbReceiver.MAC_ADDRESS, macAddress);
                    (MainActivity.context).sendBroadcast(intent);

                    // reset packet
                    packetState = 0;
                    data = new StringBuilder();
                }

            }
            else {
                Log.e(TAG, "Failed to Read from central computer. Terminating read thread.");
                break;
            }
        }

    }
    private void communicationSetup() {
        for (int i = 0; i < device.getInterfaceCount(); i++) {
            if (device.getInterface(i).getInterfaceClass() == UsbConstants.USB_CLASS_CDC_DATA) {
                Log.i(TAG, "Cdc interface found");
                usbInterface = device.getInterface(i);

                for (int j = 0; j < usbInterface.getEndpointCount(); j++) {
                    if (usbInterface.getEndpoint(j).getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                        if(usbInterface.getEndpoint(j).getDirection() == UsbConstants.USB_DIR_IN) {
                            Log.i(TAG, "Read endpoint found");
                            readEndpoint = usbInterface.getEndpoint(j);
                        }
                    }
                }
            }
        }
    }
}

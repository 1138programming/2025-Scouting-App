package com.scouting_app_2025.Bluetooth;

import static androidx.core.content.ContextCompat.getSystemService;

import static com.scouting_app_2025.MainActivity.TAG;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.scouting_app_2025.MainActivity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class QrBtConnThread extends Thread {
    static BluetoothSocket socket;
    public QrBtConnThread() {

    }

    /**
     * Creates a BluetoothConnectedThread based on a given MAC address and port.
     *
     * @param mac The MAC address of the device
     * @param port The bluetooth port of the  on that device
     */
     public static void bluetoothConnect(String mac, int port) {
        if(!((MainActivity) MainActivity.context).permissionManager.checkPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            Log.e(TAG, "need permission for Bluetooth_Connect");
        }
        BluetoothSocket tmp;
        BluetoothDevice device = ((BluetoothManager) MainActivity.context.getSystemService(Context.BLUETOOTH_SERVICE))
                 .getAdapter().getRemoteDevice(mac);

        try {
            Method method = device.getClass().getMethod("createInsecureRfcommSocket", int.class);
            tmp = (BluetoothSocket) method.invoke(device, port);
            socket = tmp;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.e(TAG,"connect method failed",e);
            return;
        }

        if(socket == null) return;

        try {
            Log.e(TAG, "badlet?");
            socket.connect();
            Log.e(TAG, "ROBERTBADLETTTTT");
        }
        catch(IOException er){
            Log.e(TAG, "Timed out/error");
            // Unable to connect; close the socket and return.
            cancel();
        }
     }

     public static void cancel() {
         try {
             socket.close();
             Log.e(TAG, "socket closed");
         } catch (IOException closeException) {
             Log.e(TAG, "couldn't close", closeException);
         }
     }
}

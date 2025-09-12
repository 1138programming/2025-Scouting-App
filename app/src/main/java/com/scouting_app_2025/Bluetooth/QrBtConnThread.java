package com.scouting_app_2025.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class QrBtConnThread extends Thread {

    static BluetoothAdapter adapter;
    static BluetoothDevice device;
    static BluetoothSocket socket;
    public QrBtConnThread() {

    }
     public static void bluetoothConnect(String mac, int port) {
         BluetoothSocket tmp;
         device = adapter.getRemoteDevice(mac);

         try {
             Method method = device.getClass().getMethod("createInsecureRfcommSocket", int.class);
             tmp = (BluetoothSocket) method.invoke(device, port);
             socket = tmp;
         } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
             Log.e("1138 SCApp","connect method failed",e);
             return;
         }

         if(socket == null) return;

         try {
             Log.e("1138 SCApp", "badlet?");
             socket.connect();
             Log.e("1138 SCApp", "ROBERTBADLETTTTT");
         }
         catch(IOException er){
             Log.e("1138 SCApp", "Timed out/error");
             // Unable to connect; close the socket and return.
             try {
                 socket.close();
                 Log.e("1138 SCApp", "socket closed");
             } catch (IOException closeException) {
                 Log.e("1138 SCApp", "couldn't close", closeException);
             }
         }
     }
}

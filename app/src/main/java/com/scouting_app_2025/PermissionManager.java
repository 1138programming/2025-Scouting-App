package com.scouting_app_2025;

import static com.scouting_app_2025.MainActivity.TAG;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PermissionManager {
    private final Context context;
    private final HashMap<String, Boolean> permissionTracker = new HashMap<>();
    private final ActivityResultLauncher<String[]> bluetoothPermissionRequest;

    public PermissionManager(Context context) {
        this.context = context;
        /* registers a permission request that automatically goes through all permissions
         * and then calls createReceiverScan() if all permissions are granted.
         */
        bluetoothPermissionRequest = ((MainActivity)context).registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                    if(!isGranted.containsValue(false)) {
                        ((MainActivity)context).createReceiverScan();
                    }
                });
    }

    /**
     * @Info:
     */
    public void addPermission(String permission) {
        permissionTracker.put(permission, false);
    }

    /**
     * @Info: Should be called once to request all permissions added
     * to the {@code PermissionManager}. Returns {@code boolean} value
     * that communicates whether all permissions have been granted.
     */
    public void requestPermissions() {
        bluetoothPermissionRequest.launch(getNeededPermissions());
    }
    /**
     * @Info: Called to check if app has a certain permission
     */
    public boolean checkPermission(String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
//    public String getPermissionStatus() {
//        StringBuilder message = new StringBuilder();
//        for(String permission : permissionTracker.keySet()) {
//            message.append(permission)
//                    .append(": ")
//                    .append(permissionTracker.get(permission))
//                    .append("\n");
//        }
//        return message.subSequence(0,message.length()-1).toString();
//    }
    private String[] getNeededPermissions() {
        ArrayList<String> neededPermissions = new ArrayList<>();
        for(String permission : permissionTracker.keySet()) {
            if(!checkPermission(permission)) {
                neededPermissions.add(permission);
            }
        }
        return neededPermissions.toArray(new String[0]);
    }
}

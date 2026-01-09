package com.scouting_app_2025;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class PermissionManager {
    private final Context context;
    private final HashMap<String, Boolean> permissionTracker = new HashMap<>();
    private final ActivityResultLauncher<String[]> bluetoothPermissionRequest;
    private static boolean hasAllPermissions = false;

    public PermissionManager(Context context) {
        this.context = context;
        /* registers a permission request that automatically goes through all permissions
         * and then calls createReceiverScan() if all permissions are granted.
         */
        bluetoothPermissionRequest = ((MainActivity)context).registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                    if(!isGranted.containsValue(false)) {
                        hasAllPermissions = true;
                    }
                });
    }

    /**
     * @Info: Adds a permission to the permission tracker
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
     * @return Returns if you have the permission or not
     */
    public boolean checkPermission(String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
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

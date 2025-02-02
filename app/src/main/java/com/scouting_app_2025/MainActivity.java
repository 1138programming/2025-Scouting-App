package com.scouting_app_2025;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.scouting_app_2025.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Team 1138 Scouting App";
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    //tracks connectivity
    private boolean connectivity = false;

    /**
     * @Info: Called to check if app has a certain permission
     */
    public boolean checkPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @Info: Updates the variable that tracks Bluetooth Connectivity
     */
    public void setConnectivity(boolean connectivity) {
        this.connectivity = connectivity;
    }

    /**
     * @Info: called when GUI element needs to be updated. This is not a switch,
     * it looks at {@code connectivity} to make sure GUI element is accurate.
     */
    private void updateConnectivity() {
        //TODO: make function update GUI
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
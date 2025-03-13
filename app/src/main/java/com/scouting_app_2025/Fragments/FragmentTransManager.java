package com.scouting_app_2025.Fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.R;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentTransManager {
    private FragmentTransaction ft;
    private final FragmentManager fm = ((MainActivity)MainActivity.context).getSupportFragmentManager();

    public FragmentTransManager(ArrayList<DataFragment> fragments) {
        ((MainActivity)MainActivity.context).setContentView(R.layout.activity_main);

        ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(R.id.main_fragment, fragment, fragment.toString());
        }
        ft.show(fragments.get(0));
        for(int i = 1; i < fragments.size(); i++) {
            ft.hide(fragments.get(i));
        }
        ft.commit();
    }

    private void showFragment(String tag) {
        ft.show(Objects.requireNonNull(fm.findFragmentByTag(tag)));
    }

    private void hideFragment(String tag) {
        ft.hide(Objects.requireNonNull(fm.findFragmentByTag(tag)));
    }

    public void preAutonNext() {
        ft = fm.beginTransaction();
        hideFragment("PreAutonFragment");
        showFragment("AutonFragment");
        ft.commit();
    }

    public void showAutonStart() {
        ft = fm.beginTransaction();
        showFragment("AutonStartFragment");
        ft.commit();
    }

    public void autonStartBack() {
        ft = fm.beginTransaction();
        hideFragment("AutonStartFragment");
        hideFragment("AutonFragment");
        showFragment("PreAutonFragment");
        ft.commit();
    }

    public void autonStartStart() {
        ft = fm.beginTransaction();
        hideFragment("AutonStartFragment");
        ft.commit();
    }

    public void autonBack() {
        ft = fm.beginTransaction();
        hideFragment("AutonFragment");
        showFragment("PreAutonFragment");
        ft.commit();
    }

    public void autonNext() {
        ft = fm.beginTransaction();
        hideFragment("AutonFragment");
        showFragment("TeleopFragment");
        ft.commit();
    }

    public void showTeleopStart() {
        ft = fm.beginTransaction();
        showFragment("TeleopStartFragment");
        ft.commit();
    }

    public void teleopStartBack() {
        ft = fm.beginTransaction();
        hideFragment("TeleopStartFragment");
        hideFragment("TeleopFragment");
        showFragment("AutonFragment");
        ft.commit();
    }

    public void teleopStartStart() {
        ft = fm.beginTransaction();
        hideFragment("TeleopStartFragment");
        ft.commit();
    }

    public void teleopBack() {
        ft = fm.beginTransaction();
        hideFragment("TeleopFragment");
        showFragment("AutonFragment");
        ft.commit();
    }

    public void teleopNext() {
        ft = fm.beginTransaction();
        hideFragment("TeleopFragment");
        showFragment("PostMatchFragment");
        ft.commit();
    }

    public void postMatchBack() {
        ft = fm.beginTransaction();
        hideFragment("PostMatchFragment");
        showFragment("TeleopFragment");
        ft.commit();
    }

    public void postMatchSubmit() {
        ft = fm.beginTransaction();
        showFragment("ConfirmSubmitFragment");
        ft.commit();
    }

    public void confirmSubmitBack() {
        ft = fm.beginTransaction();
        hideFragment("ConfirmSubmitFragment");
        ft.commit();
    }

    public void preAutonMenu() {
        ft = fm.beginTransaction();
        hideFragment("PreAutonFragment");
        showFragment("ArchiveFragment");
        ft.commit();
    }

    public void archiveFragmentBack() {
        ft = fm.beginTransaction();
        hideFragment("ArchiveFragment");
        showFragment("PreAutonFragment");
        ft.commit();
    }
}

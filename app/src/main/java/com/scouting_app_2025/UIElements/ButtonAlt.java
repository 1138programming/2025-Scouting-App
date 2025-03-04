package com.scouting_app_2025.UIElements;


import java.util.ArrayList;

public class ButtonAlt {
    private final Button button;
    private final ArrayList<Integer> datapointIDs = new ArrayList<>();
    private final ArrayList<Integer> colors = new ArrayList<>();
    private final ArrayList<Integer> counters = new ArrayList<>();
    private int currentProfile = 0;
    public ButtonAlt(Button button) {
        this.button = button;
        this.establishBaseProfile();
    }

    private void establishBaseProfile() {
        datapointIDs.add(button.getID());
        colors.add(button.getColor());
        if(button.isDataTracking()) {
            counters.add(button.getCounter());
        }
    }

    public void addProfile(int datapointID, int color) {
        datapointIDs.add(datapointID);
        colors.add(color);
        if(button.isDataTracking()) {
            counters.add(button.getCounter());
        }
    }

    public void setProfile(int profileIndex) {
        if(profileIndex < colors.size()) {
            button.setColor(colors.get(profileIndex));
            button.setID(datapointIDs.get(profileIndex));
            if(button.isDataTracking()) {
                counters.set(currentProfile, button.getCounter());
                button.setCounter(counters.get(profileIndex));
            }
        }
        else {
            button.setColor(colors.get(colors.size()-1));
            button.setID(datapointIDs.get(datapointIDs.size()-1));

        }
        currentProfile = profileIndex;
    }
    public void cycleProfile() {
        currentProfile = (currentProfile < datapointIDs.size()-1) ? currentProfile + 1 : 0;
        if(button.isDataTracking()) {
            if(currentProfile == 0) {
                counters.set(counters.size()-1, button.getCounter());
                button.setCounter(counters.get(currentProfile));
            }
            else {
                counters.set(currentProfile-1, button.getCounter());
                button.setCounter(counters.get(currentProfile));
            }
        }
        button.setColor(colors.get(currentProfile));
        button.setID(datapointIDs.get(currentProfile));
    }

    public int getCurrentProfile() {
        return currentProfile;
    }
    public int getIndex(int datapointID) {
        return datapointIDs.indexOf(datapointID);
    }

    public int getCounter(int index) {
        return counters.get(index);
    }

    public void setCounter(int index, int value) {
        counters.set(index, value);
    }
}

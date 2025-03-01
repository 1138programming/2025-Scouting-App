package com.scouting_app_2025.UIElements;

import java.util.ArrayList;

public class ButtonAlt {
    private final Button button;
    private final ArrayList<Integer> datapointIDs = new ArrayList<>();
    private final ArrayList<Integer> colors = new ArrayList<>();
    private int currentProfile = 0;
    public ButtonAlt(Button button) {
        this.button = button;
        this.establishBaseProfile();
    }

    private void establishBaseProfile() {
        datapointIDs.add(button.getID());
        colors.add(button.getColor());
    }

    public void addProfile(int datapointID, int color) {
        datapointIDs.add(datapointID);
        colors.add(color);
    }

    public void setProfile(int profileIndex) {
        currentProfile = profileIndex;
        button.setColor(colors.get(currentProfile));
        button.setID(datapointIDs.get(currentProfile));
    }
    public void cycleProfile() {
        currentProfile = (currentProfile < datapointIDs.size()-1) ? currentProfile + 1 : 0;
        button.setColor(colors.get(currentProfile));
        button.setID(datapointIDs.get(currentProfile));
    }
}

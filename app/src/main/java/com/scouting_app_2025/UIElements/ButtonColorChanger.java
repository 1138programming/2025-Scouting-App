package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.calendar;

import java.util.HashMap;
import java.util.Objects;

public class ButtonColorChanger {
    private final int secondaryColor;
    private boolean changedColor = false;
    private boolean lockedChanging = false;
    private final HashMap<Button, Integer> buttons = new HashMap<>();
    private long lastClicked = 0;
    private int colorBuffer = 250;
    public ButtonColorChanger(Button button, int color) {
        this.secondaryColor = color;
        button.setOnClickFunction(this::updateColorChanger);
    }
    public void addButton(Button button) {
        button.setOnClickFunction(this::otherButtonPressed);
        buttons.put(button, button.getColor());
    }

    public void otherButtonPressed() {
        if(!lockedChanging) {
            changeColor();
        }
    }

    public void updateColorChanger() {
        long currClicked = calendar.getTimeInMillis();
        if(currClicked - lastClicked > colorBuffer) {
            changeColor();
        }
        else {
            lockedChanging = !lockedChanging;
        }
        lastClicked = currClicked;
    }

    private void changeColor() {
        for(Button button : buttons.keySet()) {
            button.setColor(changedColor ? Objects.requireNonNull(buttons.get(button)) : secondaryColor);
        }
        changedColor = !changedColor;
    }

    public void setColorBuffer(int bufferLength) {
        this.colorBuffer = bufferLength;
    }
}

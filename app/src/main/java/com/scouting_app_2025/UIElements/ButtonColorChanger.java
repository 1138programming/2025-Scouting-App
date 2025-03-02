package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.calendar;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;

public class ButtonColorChanger {
    private final ArrayList<Drawable> secondaryColors;
    private boolean changedColor = false;
    private boolean lockedChanging = false;
    private final HashMap<Button, Drawable> buttons = new HashMap<>();
    private long lastClicked = 0;
    private int colorBuffer = 250;
    public ButtonColorChanger(Button button, ArrayList<Drawable> colors) {
        secondaryColors = colors;
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
            lockedChanging = false;
        }
        else {
            lockedChanging = true;
            setColor(1);
        }
        lastClicked = currClicked;
    }

    private void changeColor() {
        for(Button button : buttons.keySet()) {
            button.cycleButtonColor();
        }
        changedColor = !changedColor;
    }
    private void setColor(int buttonIndex) {
        for(Button button : buttons.keySet()) {
            button.changeButtonColor(buttonIndex);
        }
    }

    public ArrayList<Drawable> getColors() {
        return secondaryColors;
    }

    public void setColorBuffer(int bufferLength) {
        this.colorBuffer = bufferLength;
    }
}

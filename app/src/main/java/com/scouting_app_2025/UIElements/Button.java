package com.scouting_app_2025.UIElements;

import android.widget.ImageButton;

public class Button extends UIElement {

    private boolean dataTracking;
    private android.widget.Button button;

    public Button(String buttonName) {
        super(buttonName);
    }

    public Button(String buttonName, android.widget.Button button) {
        super(buttonName);
        this.button = button;
    }

    public boolean undo() {
        decrementPickup();
        return false;
    }

    public boolean redo() {
        incrementPickup(true);
        return false;
    }

    private boolean incrementPickup(boolean undo) {
        String text = button.getText().toString().substring(8);
        int num = Integer.parseInt(text);
        num++;
        if (num > 99) {
            num = 99;
            undo = false;
        }
        text = button.getText().toString().substring(0,8)+String.valueOf(num);
        button.setText(text);
        return undo;
    }
    private void decrementPickup() {
        String text = button.getText().toString().substring(8);
        int num = Integer.parseInt(text);
        if (num > 0) {
            num--;
        }
        text = button.getText().toString().substring(0,8)+String.valueOf(num);
        button.setText(text);
    }
}

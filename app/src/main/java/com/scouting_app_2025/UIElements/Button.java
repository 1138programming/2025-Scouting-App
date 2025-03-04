package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.TAG;
import static com.scouting_app_2025.MainActivity.datapointEventValue;
import static com.scouting_app_2025.UIElements.DatapointIDs.datapointIDs;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.scouting_app_2025.MainActivity;

public class Button<T extends View> extends UIElement {
    private final T button;
    private final UndoStack undostack;
    private final ButtonAlt buttonAlt;
    private final boolean dataTracking;
    private final int titleLength;
    private int maxValue = 99;
    private int minValue = 0;
    public Button(int datapointID, T button, UndoStack undoStack) {
        super(datapointID);
        this.undostack = undoStack;
        this.button = button;
        this.dataTracking = true;
        this.buttonAlt = new ButtonAlt(this);
        if(button instanceof android.widget.Button) {
            this.titleLength = ((android.widget.Button)button).getText().length() - 1;
        }
        else {
            this.titleLength = 0;
        }

        button.setOnClickListener(view -> clicked());
    }

    public Button(int datapointID, T button) {
        super(datapointID);
        this.button = button;
        this.undostack = null;
        this.dataTracking = false;
        this.buttonAlt = new ButtonAlt(this);
        this.titleLength = 0;
        button.setOnClickListener(view -> clicked());
    }



    @Override
    public void clicked() {
        if(increment(datapointID)) {
            undostack.addTimestamp(this);
        }
        super.clicked();
    }

    public int getColor() {
        return button.getBackgroundTintList().getDefaultColor();
    }

    public void setColor(int color) {
        button.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public void addAlt(int datapointID, int color) {
        buttonAlt.addProfile(datapointID, color);
    }

    public void changeButtonColor(int profileIndex) {
        buttonAlt.setProfile(profileIndex);
    }

    public void cycleButtonColor() {
        buttonAlt.cycleProfile();
    }

    /**
     * @Info: Called by {@link UndoStack} to decrease the value displayed on the button.
     */

    public void undo(int datapointID) {
        decrement(datapointID);
        Toast.makeText((MainActivity.context), "Undid " + datapointIDs.get(datapointID), Toast.LENGTH_SHORT).show();
    }

    /**
     * @Info: Called by {@link UndoStack} to increase the value displayed on the button.
     */
    public void redo(int datapointID) {
        increment(datapointID);
        Toast.makeText((MainActivity.context), "Redid " + datapointIDs.get(datapointID), Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getValue() {
        return datapointEventValue;
    }

    public int getCounter() {
        if(button instanceof android.widget.Button) {
            return Integer.parseInt(((android.widget.Button) button).getText().toString().substring(titleLength));
        }
        else return 0;
    }

    public void setCounter(int value) {
        if(value > maxValue) {
            value = maxValue;
        }

        if(button instanceof android.widget.Button) {
            String title = ((android.widget.Button) button).getText().toString().substring(0,titleLength) + value;
            ((android.widget.Button) button).setText(title);
        }
    }

    public boolean isDataTracking() {
        return dataTracking;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * @Info: Called to increment value of the button. If the value exceeds a maximum
     * value it remains at that value.
     * @return Returns a {@code boolean} for whether or not the button's value
     * was updated or not due to it being at max value.
     */
    private boolean increment(int datapointID) {
        if(!(button instanceof android.widget.Button)) return false;
        if(!dataTracking) return false;
        int index = buttonAlt.getIndex(datapointID);
        if(index == -1) return false;

        boolean updated = false;
        if(index == buttonAlt.getCurrentProfile()) {
            String text = ((android.widget.Button) button).getText().toString();
            int num = Integer.parseInt(text);
            if (num < maxValue) {
                num++;
                updated = true;
            }
            text = String.valueOf(num);
            ((android.widget.Button) button).setText(text);
            return updated;
        }
        else {
            int counter = buttonAlt.getCounter(index);
            if(counter < maxValue) {
                counter++;
                updated = true;
            }
            buttonAlt.setCounter(index, counter);
        }
        return updated;
    }

    /**
     * @Info: Called to decrement value of the button. If the value goes below a minimum value
     * (zero by default), it remains at the minimum. This doesn't have a {@code boolean}
     * to track if the decrement was successful because this is only used by {@link Button#undo()}
     */
    private void decrement(int datapointID) {
        if(!(button instanceof android.widget.Button)) return;
        int index = buttonAlt.getIndex(datapointID);
        if(index == -1) return;

        if(index == buttonAlt.getCurrentProfile()) {
            String text = ((android.widget.Button) button).getText().toString();
            int num = Integer.parseInt(text);
            if (num > minValue) {
                num--;
            }
            text = String.valueOf(num);
            ((android.widget.Button) button).setText(text);
        }
        else {
            int counter = buttonAlt.getCounter(index);
            if(counter > minValue) {
                counter--;
            }
            buttonAlt.setCounter(index, counter);
        }
    }
}

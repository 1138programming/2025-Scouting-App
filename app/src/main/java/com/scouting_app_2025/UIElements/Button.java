package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.datapointEventValue;
import static com.scouting_app_2025.DatapointIDs.DatapointIDs.datapointIDs;

import android.content.res.ColorStateList;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.scouting_app_2025.MainActivity;

import java.util.Objects;

public class Button extends UIElement {
    private final android.widget.Button binding;
    private final UndoStack undostack;
    private final boolean dataTracking;
    private final int titleLength;
    private int currValue;
    private int maxValue = 99;
    private int minValue = 0;
    private int color;

    /**
     * This constructor is used to create an independent button and takes a
     * binding since it's the only datapoint for the given button binding. It
     * is also a data button so it takes an UndoStack.
     * 
     * @param datapointID datapointID of the button
     * @param binding binding of the button
     * @param undoStack undoStack of the given fragment
     */
    public Button(int datapointID, @Nullable android.widget.Button binding, UndoStack undoStack) {
        super(datapointID);
        this.binding = binding;
        this.undostack = undoStack;
        this.dataTracking = true;
        if(this.binding != null) {
            this.titleLength = this.binding.length() - 1;
            this.color = Objects.requireNonNull(this.binding.getBackgroundTintList()).getDefaultColor();
            this.binding.setOnClickListener(view -> clicked());
        }
        else {
            this.titleLength = 0;
        }
    }

    /**
     * This constructor is used to create an independent button and takes a 
     * binding since it's the only datapoint for the given button binding. It
     * doesn't take an UndoStack as it doesn't track data and is for UI purposes 
     * only.
     * 
     * @param datapointID datapointID of the button (should be negative given that 
     *                    the button doesn't store data)
     * @param binding binding of the button
     */
    public Button(int datapointID, @Nullable android.widget.Button binding) {
        super(datapointID);
        this.binding = binding;
        this.undostack = null;
        this.dataTracking = false;
        if(this.binding != null) {
            this.titleLength = this.binding.length() - 1;
            this.color = Objects.requireNonNull(this.binding.getBackgroundTintList()).getDefaultColor();
            binding.setOnClickListener(view -> clicked());
        }
        else {
            this.titleLength = 0;
        }
    }
    
    @Override
    public void clicked() {
        if(increment()) {
            undostack.addTimestamp(this);
        }
        super.clicked();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        if(binding != null) {
            binding.setBackgroundTintList(ColorStateList.valueOf(this.color));
        }
    }

    /**
     * @Info: Called by {@link UndoStack} to decrease the value displayed on the button.
     */
    @Override
    public void undo() {
        decrement();
        Toast.makeText((MainActivity.context), "Undid " + datapointIDs.get(datapointID), Toast.LENGTH_SHORT).show();
    }

    /**
     * @Info: Called by {@link UndoStack} to increase the value displayed on the button.
     */
    public void redo() {
        increment();
        Toast.makeText((MainActivity.context), "Redid " + datapointIDs.get(datapointID), Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @return Returns the default datapoint event value.
     */
    @Override
    public String getValue() {
        return datapointEventValue;
    }

    /**
     *
     * @return Returns the current value of the counter
     */
    public int getCounter() {
        return currValue;
    }

    public void setCounter(int value) {
        currValue = Math.min(value, maxValue);
        if(binding != null) {
            CharSequence temp = binding.getText().subSequence(0,titleLength) + String.valueOf(currValue);
            binding.setText(temp);
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
    private boolean increment() {
        if(!dataTracking) return false;

        boolean updated = false;

        if (currValue < maxValue) {
            currValue++;
            updated = true;
        }

        if(binding != null) {
            binding.setText(String.valueOf(currValue));
        }
        return updated;
    }

    /**
     * @Info: Called to decrement value of the button. If the value goes below a minimum value
     * (zero by default), it remains at the minimum. This doesn't have a {@code boolean}
     * to track if the decrement was successful because this is only used by {@link Button#undo()}
     */
    private void decrement() {
        if(currValue > minValue) {
            currValue--;
        }

        if(binding != null) {
            binding.setText(String.valueOf(currValue));
        }
    }
}

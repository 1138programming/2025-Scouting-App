package com.scouting_app_2025.UIElements;

import android.content.Context;

public class Button extends UIElement {
    private final android.widget.Button button;
    private final UndoStack undostack;
    private final boolean dataTracking;
    private int maxValue = 99;
    private int minValue = 0;
    public Button(int datapointID, android.widget.Button button, Context context, UndoStack undoStack) {
        super(datapointID, context);
        this.button = button;
        this.undostack = undoStack;
        this.dataTracking = true;
        button.setOnClickListener(view -> clicked());
    }

    public Button(int datapointID, android.widget.Button button, Context context) {
        super(datapointID, context);
        this.button = button;
        this.undostack = null;
        this.dataTracking = false;
    }

    @Override
    public void clicked() {
        if(increment()) {
            undostack.addTimestamp(this);
        }
    }

    /**
     * @Info: Called by {@link UndoStack} to decrease the value displayed on the button.
     */
    @Override
    public void undo() {
        decrement();
    }

    /**
     * @Info: Called by {@link UndoStack} to increase the value displayed on the button.
     */
    @Override
    public void redo() {
        increment();
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
        String text = button.getText().toString();
        boolean updated = true;
        int num = Integer.parseInt(text);
        if (num < maxValue) {
            num++;
            updated = false;
        }
        text = String.valueOf(num);
        button.setText(text);
        return updated;
    }

    /**
     * @Info: Called to decrement value of the button. If the value goes below a minimum value
     * (zero by default), it remains at the minimum. This doesn't have a {@code boolean}
     * to track if the decrement was successful because this is only used by {@link Button#undo()}
     */
    private void decrement() {
        String text = button.getText().toString();
        int num = Integer.parseInt(text);
        if (num > minValue) {
            num--;
        }
        text = String.valueOf(num);
        button.setText(text);
    }
}

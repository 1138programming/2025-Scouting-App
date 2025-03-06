package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.TAG;

import android.util.Log;

public class Checkbox extends UIElement {
    private final android.widget.CheckBox checkbox;
    private final boolean locking;
    private final UndoStack undoStack;
    private String nameValue;
    public Checkbox(int datapointID, android.widget.CheckBox checkbox, boolean locking, UndoStack undoStack) {
        super(datapointID);
        this.checkbox = checkbox;
        this.locking = locking;
        this.undoStack = undoStack;
        checkbox.setOnClickListener(view -> clicked());
    }

    public Checkbox(int datapointID, android.widget.CheckBox checkbox, String name) {
        super(datapointID);
        this.checkbox = checkbox;
        this.locking = false;
        this.undoStack = null;
        this.nameValue = name;
        checkbox.setOnClickListener(view -> super.clicked());
    }

    @Override
    public void clicked() {
        Log.d(TAG, "check clicked");
        if(!checkbox.isEnabled()) super.clicked();
        if (locking) {
            if(checkbox.isChecked()) {
                checkbox.setEnabled(false);
                undoStack.addTimestamp(this);
            }
        }
    }

    public void setChecked(boolean checked) {
        checkbox.setChecked(checked);
    }

    public boolean isChecked() {
        return checkbox.isChecked();
    }
    @Override
    public String getValue() {
        if(undoStack == null) return nameValue;
        else return Boolean.toString(checkbox.isChecked());
    }

    /**
     * @Info: Called by both {@link Checkbox#undo()} and {@link Checkbox#redo()} functions
     * to update the status of the checkbox
     */
    @Override
    public void undo() {
        if(locking) {
            checkbox.setChecked(false);
            checkbox.setEnabled(true);
        }
    }
    @Override
    public void redo() {
        if(locking) {
            checkbox.setChecked(true);
            checkbox.setEnabled(false);
        }
    }
}

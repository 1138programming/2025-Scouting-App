package com.scouting_app_2025.UIElements;

public class Checkbox extends UIElement {
    private final android.widget.CheckBox checkbox;
    private final boolean locking;
    private final UndoStack undoStack;
    public Checkbox(int datapointID, android.widget.CheckBox checkbox, boolean locking, UndoStack undoStack) {
        super(datapointID);
        this.checkbox = checkbox;
        this.locking = locking;
        this.undoStack = undoStack;
        checkbox.setOnClickListener(view -> clicked());
    }

    @Override
    public void clicked() {
        checkbox.setChecked(true);
        if(locking) {
            checkbox.setEnabled(false);

        }
    }
    @Override
    public void undo() {
        checkbox.setChecked(false);
        if(locking) {
            checkbox.setEnabled(true);
        }
    }
    @Override
    public void redo() {
        checkbox.setChecked(true);
        if(locking) {
            checkbox.setEnabled(false);
        }
    }

}

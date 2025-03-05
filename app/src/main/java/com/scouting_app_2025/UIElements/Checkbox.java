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
        super.clicked();
        if (locking) {
            if(!checkbox.isChecked()) {
                checkbox.setEnabled(false);
                undoStack.addTimestamp(this);
            }
        }
    }

    @Override
    public String getValue() {
        return Boolean.toString(checkbox.isChecked());
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

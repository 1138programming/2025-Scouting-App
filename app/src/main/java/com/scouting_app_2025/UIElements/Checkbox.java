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
        if (locking) {
            if(!checkbox.isChecked()) {
                checkbox.setChecked(true);
                checkbox.setEnabled(false);
            }
        }
        else {
            undoStack.addTimestamp(this);
            checkbox.setChecked(!checkbox.isChecked());
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
    private void undoRedo() {
        checkbox.setChecked(!checkbox.isChecked());
        if(locking) {
            checkbox.setEnabled(!checkbox.isEnabled());
        }
    }
    @Override
    public void undo() {
        undoRedo();
    }
    @Override
    public void redo() {
        undoRedo();
    }

}

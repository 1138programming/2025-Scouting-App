package com.scouting_app_2025.UIElements;

import android.content.Context;

public class GUIManager {
    UndoStack undoStack;
    Context context;
    public GUIManager(Context context) {
        this.context = context;
        undoStack = new UndoStack();
    }

    public void createButton(String buttonName, android.widget.Button button) {
        undoStack.addElement(new Button(buttonName, button, undoStack));
    }

    public void createCheckbox(String checkboxName, android.widget.CheckBox checkbox, boolean locked) {
        undoStack.addElement(new Checkbox(checkboxName, checkbox, locked, undoStack));
    }
}

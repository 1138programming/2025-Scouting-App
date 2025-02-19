package com.scouting_app_2025.UIElements;

import android.content.Context;

import java.util.ArrayList;

public class GUIManager {
    UndoStack undoStack;
    Context context;
    ArrayList<UIElement> nonStackElements = new ArrayList<UIElement>();
    public GUIManager(Context context) {
        this.context = context;
        undoStack = new UndoStack();
    }

    public void createDataButton(int datapointID, android.widget.Button button) {
        undoStack.addElement(new Button(datapointID, button, context, undoStack));
    }

    public void createCheckbox(int datapointID, android.widget.CheckBox checkbox, boolean locked) {
        undoStack.addElement(new Checkbox(datapointID, checkbox, locked, context, undoStack));
    }

    public void createSpinner(int datapointID, android.widget.Spinner spinner) {
        nonStackElements.add(new Spinner(datapointID, spinner, context));
    }

    public void getTabletInformation() {

    }
}

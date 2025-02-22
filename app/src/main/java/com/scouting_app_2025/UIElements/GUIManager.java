package com.scouting_app_2025.UIElements;

import android.content.Context;

import java.util.ArrayList;

public class GUIManager {
    UndoStack undoStack;
    Context context;
    ArrayList<Spinner> tabletInfoElements = new ArrayList<>();
    ArrayList<UIElement> nonStackElements = new ArrayList<>();
    public GUIManager(Context context) {
        this.context = context;
        undoStack = new UndoStack();
    }

    public void createDataButton(int datapointID, android.widget.Button button) {
        undoStack.addElement(new Button(datapointID, button, context, undoStack));
    }
    public void createNonDataButton(int datapointID, android.widget.Button button) {
        nonStackElements.add(new Button(datapointID, button, context));
    }

    public void createCheckbox(int datapointID, android.widget.CheckBox checkbox, boolean locked) {
        undoStack.addElement(new Checkbox(datapointID, checkbox, locked, context, undoStack));
    }

    public void createSpinner(int datapointID, android.widget.Spinner spinner) {
        nonStackElements.add(new Spinner(datapointID, spinner, context));
    }
    public void createTabletInfoSpinner(int datapointID, android.widget.Spinner spinner) {
        tabletInfoElements.add(new Spinner(datapointID, spinner, context));
    }

    public String getTabletInformation() {
        StringBuilder tabletInfo = new StringBuilder();
        for(Spinner element : tabletInfoElements) {
            tabletInfo.append(element.getValue());
            tabletInfo.append(": ");
        }
        return tabletInfo.delete(tabletInfo.length()-2,tabletInfo.length()).toString();
    }
}

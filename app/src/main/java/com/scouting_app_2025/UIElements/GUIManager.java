package com.scouting_app_2025.UIElements;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GUIManager {
    UndoStack undoStack;
    Context context;
    ArrayList<Spinner> tabletInfoElements = new ArrayList<>();
    ArrayList<UIElement> nonStackElements = new ArrayList<>();
    HashMap<Integer, ButtonColorChanger> colorChangers = new HashMap<>();
    public GUIManager(Context context) {
        this.context = context;
        undoStack = new UndoStack();
    }

    public void createColorChanger(int changerID, int datapointID, android.widget.Button button, boolean dataStoring, int color) {
        createButton(datapointID, button, dataStoring);
        colorChangers.put(changerID, new ButtonColorChanger((Button)undoStack.getElement(datapointID), color));
    }
    public void createColorChangerButton(int changerID, int datapointID, android.widget.Button button, boolean dataStoring) {
        createButton(datapointID, button, dataStoring);
        Objects.requireNonNull(colorChangers.get(changerID)).addButton((Button)undoStack.getElement(datapointID));
    }

    public void createButton(int datapointID, android.widget.Button button, boolean dataStoring) {
        if(dataStoring) {
            undoStack.addElement(new Button(datapointID, button, context, undoStack));
        }
        else {
            nonStackElements.add(new Button(datapointID, button, context));
        }
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

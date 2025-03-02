package com.scouting_app_2025.UIElements;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GUIManager {
    UndoStack undoStack;
    Context context;
    ArrayList<Spinner> tabletInfoElements = new ArrayList<>();
    HashMap<Integer, UIElement> nonStackElements = new HashMap<>();
    HashMap<Integer, ButtonColorChanger> colorChangers = new HashMap<>();
    public GUIManager(Context context) {
        this.context = context;
        undoStack = new UndoStack();
    }

    public int createColorChanger(ArrayList<Integer> changerButtonIDs, android.widget.Button button, boolean dataStoring, ArrayList<Drawable> colors) {
        createButton(changerButtonIDs.get(0), button, dataStoring);
        Button createdButton = (Button)Objects.requireNonNull((dataStoring ? undoStack.getElement(changerButtonIDs.get(0))
                : nonStackElements.get(changerButtonIDs.get(0))));
        colorChangers.put(colorChangers.size(), new ButtonColorChanger(createdButton, colors));
        for(int i = 1; i < changerButtonIDs.size() && i < colors.size(); i++) {
            createdButton.addAlt(changerButtonIDs.get(i), colors.get(i));
        }
        return colorChangers.size()-1;
    }
    public void createColorChangerButton(int changerButtonID, ArrayList<Integer> datapointIDs, android.widget.Button button, boolean dataStoring) {
        createButton(datapointIDs.get(0), button, dataStoring);
        Button createdButton = (Button)Objects.requireNonNull((dataStoring ?
                undoStack.getElement(datapointIDs.get(0)) : nonStackElements.get(datapointIDs.get(0))));

        Objects.requireNonNull(colorChangers.get(changerButtonID)).addButton(createdButton);
        ArrayList<Drawable> colors = Objects.requireNonNull(colorChangers.get(changerButtonID)).getColors();
        for(int i = 1; i < datapointIDs.size() && i < colors.size(); i++) {
            createdButton.addAlt(datapointIDs.get(i), colors.get(i));
        }
    }

    public void createButton(int datapointID, android.widget.Button button, boolean dataStoring) {
        if(dataStoring) {
            undoStack.addElement(new Button<>(datapointID, button, context, undoStack));
        }
        else {
            nonStackElements.put(datapointID, new Button<>(datapointID, button, context));
        }
    }

    public void createCheckbox(int datapointID, android.widget.CheckBox checkbox, boolean locked) {
        undoStack.addElement(new Checkbox(datapointID, checkbox, locked, context, undoStack));
    }

    public void createSpinner(int datapointID, android.widget.Spinner spinner) {
        nonStackElements.put(datapointID, new Spinner(datapointID, spinner, context));
    }
    public void createTabletInfoSpinner(int datapointID, android.widget.Spinner spinner) {
        tabletInfoElements.add(new Spinner(datapointID, spinner, context));
    }

    public void addAction(int elementID, Runnable runnable) {
        if(elementID > 0) {
            undoStack.getElement(elementID).setOnClickFunction(runnable);
        }
        else {
            Objects.requireNonNull(nonStackElements.get(elementID)).setOnClickFunction(runnable);
        }
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

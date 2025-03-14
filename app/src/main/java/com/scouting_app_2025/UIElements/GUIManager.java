package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.TAG;

import android.util.Log;
import android.view.View;

import com.scouting_app_2025.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GUIManager {
    UndoStack undoStack;
    HashMap<Integer, Spinner> tabletInfoElements = new HashMap<>();
    HashMap<Integer, UIElement> nonStackElements = new HashMap<>();
    HashMap<Integer, ButtonColorChanger> colorChangers = new HashMap<>();
    public GUIManager() {
        undoStack = new UndoStack();
    }

    public void createColorChanger(ArrayList<Integer> changerButtonIDs, android.widget.Button button, boolean dataStoring, ArrayList<Integer> colors) {
        createButton(changerButtonIDs.get(0), button, dataStoring);
        Button createdButton = (Button)Objects.requireNonNull((dataStoring ? undoStack.getElement(changerButtonIDs.get(0))
                : nonStackElements.get(changerButtonIDs.get(0))));
        colors.add(0, button.getBackgroundTintList().getDefaultColor());
        colorChangers.put(changerButtonIDs.get(0), new ButtonColorChanger(createdButton, colors));
        for(int i = 1; i < changerButtonIDs.size() && i < colors.size(); i++) {
            createdButton.addAlt(changerButtonIDs.get(i), colors.get(i));
        }
    }
    public void createColorChangerButton(int changerButtonID, ArrayList<Integer> datapointIDs, android.widget.Button button, boolean dataStoring) {
        createButton(datapointIDs.get(0), button, dataStoring);
        Button createdButton = (Button)Objects.requireNonNull((dataStoring ?
                undoStack.getElement(datapointIDs.get(0)) : nonStackElements.get(datapointIDs.get(0))));

        Objects.requireNonNull(colorChangers.get(changerButtonID)).addButton(createdButton);
        ArrayList<Integer> colors = Objects.requireNonNull(colorChangers.get(changerButtonID)).getColors();
        for(int i = 1; i < datapointIDs.size() && i < colors.size(); i++) {
            createdButton.addAlt(datapointIDs.get(i), colors.get(i));
        }
    }

    public <T extends View> void createUndoRedoButton(int datapointID, T button, boolean undo) {
        createButton(datapointID, button, false);
        if(undo) {
            Objects.requireNonNull(nonStackElements.get(datapointID)).setOnClickFunction(() -> undoStack.undo());
        }
        else {
            Objects.requireNonNull(nonStackElements.get(datapointID)).setOnClickFunction(() -> undoStack.redo());
        }
    }

    public <T extends View> void createButton(int datapointID, T button, boolean dataStoring) {
        if(dataStoring) {
            undoStack.addElement(new Button<>(datapointID, button, undoStack));
        }
        else {
            nonStackElements.put(datapointID, new Button<>(datapointID, button));
        }
    }

    public void createCheckbox(int datapointID, android.widget.CheckBox checkbox, boolean startingState, boolean locked) {
        undoStack.addElement(new Checkbox(datapointID, checkbox, startingState, locked, undoStack));
    }

    public void createRadioGroup(int datapointID, android.widget.RadioGroup radioGroup) {
        if(datapointID > 0) {
            undoStack.addElement(new RadioGroup(datapointID, radioGroup));
        }
        else {
            nonStackElements.put(datapointID, new RadioGroup(datapointID, radioGroup));
        }
    }

    public void createRadioCheckboxGroup(int groupDatapointID) {
        undoStack.addElement(new RadioCheckboxGroup(groupDatapointID));
    }

    public void createCheckboxInGroup(int groupDatapointID, int datapointID, android.widget.CheckBox checkbox, boolean startingState, String name) {
        nonStackElements.put(datapointID, new Checkbox(datapointID, checkbox, startingState, name));
        ((RadioCheckboxGroup)undoStack.getElement(groupDatapointID)).addElement(nonStackElements.get(datapointID));
    }

    public void createRadioGroupInGroup(int groupDatapointID, int datapointID, android.widget.RadioGroup radioGroup) {
        nonStackElements.put(datapointID, new RadioGroup(datapointID, radioGroup));
        ((RadioCheckboxGroup)undoStack.getElement(groupDatapointID)).addElement(nonStackElements.get(datapointID));
    }

    public void setSelectedInRadioCheckboxGroup(int groupDatapointID, int datapointID) {
        ((RadioCheckboxGroup)undoStack.getElement(groupDatapointID)).selectElement(nonStackElements.get(datapointID));
    }

    public void createSpinner(int datapointID, android.widget.Spinner spinner, boolean addOther) {
        if(datapointID > 0) {
            undoStack.addElement(new Spinner(datapointID, spinner, addOther));
        }
        else {
            nonStackElements.put(datapointID, new Spinner(datapointID, spinner, addOther));
        }
    }
    public void createTabletInfoSpinner(int datapointID, android.widget.Spinner spinner, boolean addOther) {
        tabletInfoElements.put(datapointID, new Spinner(datapointID, spinner, addOther));
    }

    public void setSpinner(int datapointID, ArrayList<CharSequence> list, boolean tabletInfo) {
        if(tabletInfo) {
            Objects.requireNonNull(tabletInfoElements.get(datapointID)).updateSpinnerList(list);
        }
        else {
            if(datapointID > 0) {
                Objects.requireNonNull((Spinner)undoStack.getElement(datapointID)).updateSpinnerList(list);
            }
            else {
                Objects.requireNonNull((Spinner)nonStackElements.get(datapointID)).updateSpinnerList(list);
            }
        }
    }

    public void addAction(int elementID, Runnable runnable) {
        if(elementID > 0) {
            undoStack.getElement(elementID).setOnClickFunction(runnable);
        }
        //if the elementID is -1, -2, or -4, then it is tablet info
        else if(elementID > -3 || elementID == -4) {
            Objects.requireNonNull(tabletInfoElements.get(elementID)).setOnClickFunction(runnable);
        }
        else {
            Objects.requireNonNull(nonStackElements.get(elementID)).setOnClickFunction(runnable);
        }
    }

    public UIElement getElement(int datapointID) {
        if(datapointID > 0) {
            return undoStack.getElement(datapointID);
        }
        //if the elementID is -1, -2, or -4, then it is tablet info
        else if(datapointID > -3 || datapointID == -4) {
            return Objects.requireNonNull(tabletInfoElements.get(datapointID));
        }
        else {
            return Objects.requireNonNull(nonStackElements.get(datapointID));
        }
    }

    public String getTabletInformation() {
        StringBuilder tabletInfo = new StringBuilder();
        for(Spinner element : tabletInfoElements.values()) {
            tabletInfo.append(element.getValue());
            tabletInfo.append(": ");
        }
        if(tabletInfo.length() > 2) {
            return tabletInfo.delete(tabletInfo.length()-2,tabletInfo.length()).toString();
        }
        else {
            return "Error getting tablet info";
        }
    }

    public JSONArray getFragmentMatchData() throws JSONException {
        return undoStack.getTimestamps(((MainActivity) MainActivity.context).getBaseJSON());
    }

    public void setTabletInfoElements(ArrayList<ArrayList<CharSequence>> info) {
        setSpinner(-1, info.get(0), true); // scouterName
        setSpinner(-4, info.get(2), true); // teamNum
    }
}

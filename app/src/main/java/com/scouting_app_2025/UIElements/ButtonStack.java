package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.DatapointIDs.DatapointIDs.nonDataIDs;

import android.content.res.ColorStateList;
import android.util.Log;

import static com.scouting_app_2025.MainActivity.TAG;

import com.scouting_app_2025.DatapointIDs.NonDataEnum;

import java.util.ArrayList;
import java.util.Objects;

public class ButtonStack extends UIElement {
    private final android.widget.Button binding;
    private final UndoStack undoStack;
    private int selectedButton = 0;
    private final ArrayList<ButtonAlt> buttons = new ArrayList<>();
    private final ArrayList<Integer> buttonDatapointIDs = new ArrayList<>();

    /**
     * Constructor for a buttonStack with at least one data button
     *
     * @param binding binding that will be home to all the button alts
     * @param undoStack undoStack that will be given to each button alt
     */
    public ButtonStack(android.widget.Button binding, UndoStack undoStack) {
        super(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ButtonStack)));
        this.binding = binding;
        this.undoStack = undoStack;
    }

    /**
     * Constructor for a buttonStack with NO DATA BUTTONS. UndoStack is not set because it is
     * not needed for buttons that don't store data.
     *
     * @param binding binding that will be home to all the button alts
     */
    public ButtonStack(android.widget.Button binding) {
        super(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ButtonStack)));

        undoStack = null;
        this.binding = binding;
    }

    public void addAlt(int datapointID, int color) {
        if(undoStack == null) {
            Log.e(TAG, "Tried to make a data storing ButtonAlt in a non-data ButtonStack");
            return;
        }
        buttons.add(new ButtonAlt(datapointID, undoStack, color, (String)binding.getText()));
        buttonDatapointIDs.add(datapointID);
    }

    public void addNonDataAlt(int datapointID, int color) {
        buttons.add(new ButtonAlt(datapointID, color, (String)binding.getText()));
        buttonDatapointIDs.add(datapointID);
    }

    public ButtonAlt getButton(int datapointID) {
        return buttons.get(buttonDatapointIDs.indexOf(datapointID));
    }

    public void cycleButton() {
        selectedButton++;
        if (selectedButton >= buttonDatapointIDs.size()) {
            selectedButton = 0;
        }
        updateButton();
    }

    public void setButton(int datapointID) {
        selectedButton = buttonDatapointIDs.indexOf(datapointID);
        updateButton();
    }

    public void updateButton() {
        ButtonAlt currButton = getButton(buttonDatapointIDs.get(selectedButton));
        binding.setText(currButton.getLabel());
        binding.setBackgroundTintList(ColorStateList.valueOf(currButton.getColor()));
    }
}

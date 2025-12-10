package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import android.content.res.ColorStateList;

import java.util.ArrayList;
import java.util.Objects;

public class ButtonStack extends UIElement {
    private final android.widget.Button binding;
    private final UndoStack undoStack;
    private final int titleLength;
    private int selectedButton = 0;
    private final ArrayList<Button> buttons = new ArrayList<>();
    private final ArrayList<Integer> buttonDatapointIDs = new ArrayList<>();

    /**
     * Constructor for a buttonStack with at least one data button
     *
     * @param datapointID datapointID of the first data button of the stack
     * @param binding binding that will be home to all the button alts
     * @param undoStack undoStack that will be given to each button alt
     */
    public ButtonStack(int datapointID, android.widget.Button binding, UndoStack undoStack) {
        super(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ButtonStack)));

        buttons.add(new Button(datapointID, undoStack,
                Objects.requireNonNull(binding.getBackgroundTintList()).getDefaultColor()));
        this.buttonDatapointIDs.add(datapointID);
        this.undoStack = undoStack;
        this.binding = binding;
        this.titleLength = this.binding.length()-1;

        buttons.get(0).setOnClickFunction(this::updateButton);
    }

    /**
     * Constructor for a buttonStack with NO DATA BUTTONS. UndoStack is not set because it is
     * not needed for buttons that don't store data.
     *
     * @param datapointID datapointID of first button alt
     * @param binding binding that will be home to all the button alts
     */
    public ButtonStack(int datapointID, android.widget.Button binding) {
        super(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ButtonStack)));
        buttons.add(new Button(datapointID,Objects.requireNonNull(binding.getBackgroundTintList()).getDefaultColor()));
        undoStack = null;
        this.binding = binding;
        this.titleLength = this.binding.length()-1;

        buttons.get(0).setOnClickFunction(this::updateButton);
    }

    public void addAlt(int datapointID, int color) {
        buttons.add(new Button(datapointID, undoStack, color));
        buttonDatapointIDs.add(datapointID);
    }

    public void addNonDataAlt(int datapointID, int color) {
        buttons.add(new Button(datapointID, color));
        buttonDatapointIDs.add(datapointID);
    }

    public Button getButton(int datapointID) {
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
        Button currButton = getButton(buttonDatapointIDs.get(selectedButton));
        CharSequence temp = binding.getText().subSequence(0,titleLength)
                + String.valueOf(currButton.getCounter());
        binding.setText(temp);
        binding.setBackgroundTintList(ColorStateList.valueOf(currButton.getColor()));
    }
}

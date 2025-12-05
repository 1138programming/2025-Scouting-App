package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.UIElements.DatapointIDs.nonDataIDs;

import java.util.ArrayList;
import java.util.Objects;

public class ButtonParent extends UIElement {
    private final android.widget.Button binding;
    private final UndoStack undoStack;
    private final ArrayList<Button> buttons = new ArrayList<>();
    private final ArrayList<Integer> buttonDatapointIDs = new ArrayList<>();

    public ButtonParent(int datapointID, android.widget.Button binding, UndoStack undoStack) {
        super(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ButtonParent)));
        buttons.add(new Button(datapointID, undoStack,
                Objects.requireNonNull(binding.getBackgroundTintList()).getDefaultColor()));
        this.buttonDatapointIDs.add(datapointID);
        this.undoStack = undoStack;
        this.binding = binding;
    }

    public ButtonParent(int datapointID, android.widget.Button binding) {
        super(Objects.requireNonNull(nonDataIDs.get(NonDataEnum.ButtonParent)));
        buttons.add(new Button(datapointID,Objects.requireNonNull(binding.getBackgroundTintList()).getDefaultColor()));
        undoStack = null;
        this.binding = binding;
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

}

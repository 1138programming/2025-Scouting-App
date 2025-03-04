package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.UIElements.DatapointIDs.datapointIDs;

import android.widget.Toast;

import com.scouting_app_2025.MainActivity;

import java.util.ArrayList;

public class UIElement {
    protected int datapointID;
    private final ArrayList<Runnable> onClickFunctions = new ArrayList<>();

    /**
     * @Info: Base constructor for UI Elements that are going to be
     * undone or redone.
     */
    public UIElement(int datapointID) {
        this.datapointID = datapointID;
    }

    public int getID() {
        return datapointID;
    }
    public void setID(int datapointID) {
        this.datapointID = datapointID;
    }
    public String getValue() {
        return "true";
    }

    public void clicked() {
        for(Runnable onClickFunction : onClickFunctions) {
            onClickFunction.run();
        }
    }
    public void setOnClickFunction(Runnable onClickFunction) {
        onClickFunctions.add(onClickFunction);
    }
    public void undo() {

    }

    public void redo() {

    }
}

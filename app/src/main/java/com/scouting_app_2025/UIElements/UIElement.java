package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.UIElements.DatapointIDs.datapointIDs;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class UIElement {
    protected int datapointID;
    private final Context context;
    private ArrayList<Runnable> onClickFunctions;

    /**
     * @Info: Base constructor for UI Elements that are going to be
     * undone or redone.
     */
    public UIElement(int datapointID, Context context) {
        this.datapointID = datapointID;
        this.context = context;
    }

    /**
     * @Info: Base constructor for UI Elements that are *NOT* going to be
     * undone or redone.
     */
    public UIElement(int datapointID) {
        this.datapointID = datapointID;
        this.context = null;
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
        Toast.makeText(context, "Undid " + datapointIDs.get(datapointID), Toast.LENGTH_SHORT).show();
    }

    public void redo() {
        Toast.makeText(context, "Redid " + datapointIDs.get(datapointID), Toast.LENGTH_SHORT).show();
    }
}

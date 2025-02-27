package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.UIElements.DatapointIDs.datapointIDs;

import android.content.Context;
import android.widget.Toast;

public class UIElement {
    protected final int datapointID;
    private final Context context;
    private Runnable onClickFunction;

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
    public String getValue() {
        return "true";
    }

    public void clicked() {
        if(onClickFunction != null) {
            onClickFunction.run();
        }
    }
    public void setOnClickFunction(Runnable onClickFunction) {
        this.onClickFunction = onClickFunction;
    }
    public void undo() {
        Toast.makeText(context, "Undid " + datapointIDs.get(datapointID), Toast.LENGTH_SHORT).show();
    }

    public void redo() {
        Toast.makeText(context, "Redid " + datapointIDs.get(datapointID), Toast.LENGTH_SHORT).show();
    }
}

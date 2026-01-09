package com.scouting_app_2025.UIElements;

public class ButtonAlt extends Button {
    private final String title;

    /**
     * This constructor is used to create a button alt under a buttonStack
     * and therefore does not take a binding as it is not the only datapointID
     * for said button and the binding is managed by the buttonStack. However,
     * it is a data button and still takes an UndoStack.
     *
     * @param datapointID datapointID of the specific button alt
     * @param undoStack undoStack of the given fragment
     * @param color color of the button alt
     */
    public ButtonAlt(int datapointID, UndoStack undoStack, int color, String title) {
        super(datapointID, null, undoStack);
        super.setColor(color);
        this.title = title.substring(0,title.length()-1);
    }

    /**
     * This constructor is used to create a button alt under a buttonStack
     * and therefore does not take a binding as it is not the only datapointID
     * for said button and the binding is managed by the buttonStack. It also
     * doesn't take an UndoStack as it does not track data and is only for UI
     * purposes.
     *
     * @param datapointID datapointID of the given button (should be negative
     *                    given that it doesn't store data)
     * @param color color of the given button alt
     */
    public ButtonAlt(int datapointID, int color, String title) {
        super(datapointID, null);
        super.setColor(color);
        this.title = title;
    }

    public String getLabel() {
        return title + super.getCounter();
    }
}

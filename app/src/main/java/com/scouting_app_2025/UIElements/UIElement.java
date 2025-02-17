package com.scouting_app_2025.UIElements;

public class UIElement {
    private final String elementName;

    public UIElement(String elementName) {
        this.elementName = elementName;
    }

    public String getName() {
        return elementName;
    }

    public boolean undo() {
        return false;
    }

    public boolean redo() {
        return false;
    }
}

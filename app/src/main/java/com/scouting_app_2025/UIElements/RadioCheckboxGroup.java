package com.scouting_app_2025.UIElements;

import java.util.ArrayList;

public class RadioCheckboxGroup extends UIElement{
    ArrayList<UIElement> elements = new ArrayList<>();
    int currSelected = -1;
    public RadioCheckboxGroup(int datapointID) {
        super(datapointID);
    }

    public void addElement(UIElement element) {
        elements.add(element);
        element.setOnClickFunction(() -> {
            elementSelected(element);
        });
    }

    public void elementSelected(UIElement element) {
        currSelected = elements.indexOf(element);
        unselectAllExceptSelected();
    }

    public void selectElement(UIElement element) {
        currSelected = elements.indexOf(element);
        unselectAllExceptSelected();
    }

    private void unselectAllExceptSelected() {
        for(int i = 0; i < elements.size(); i++) {
            if(i != currSelected) {
                if (elements.get(i) instanceof Checkbox) {
                    ((Checkbox) elements.get(i)).setChecked(false);
                }
                else if (elements.get(i) instanceof RadioGroup) {
                    ((RadioGroup) elements.get(i)).unselect();
                    ((Checkbox) elements.get(0)).setChecked(true);
                }
            }
        }
    }

    @Override
    public String getValue() {
        if(currSelected == -1 || elements.isEmpty()) return "";
        return elements.get(currSelected).getValue();
    }
}

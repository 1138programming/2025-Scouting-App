package com.scouting_app_2025.UIElements;

import static com.scouting_app_2025.MainActivity.TAG;

import android.util.Log;
import android.widget.RadioButton;

import com.scouting_app_2025.MainActivity;


public class RadioGroup extends UIElement {
    private final android.widget.RadioGroup radioGroup;
    public RadioGroup(int datapointID, android.widget.RadioGroup radioGroup) {
        super(datapointID);
        this.radioGroup = radioGroup;
        this.radioGroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.RadioGroup group, int checkedId) {
                if(checkedId != -1) {
                    RadioGroup.super.clicked();
                }
            }
        });
    }

    public void unselect() {
        radioGroup.clearCheck();
    }

    @Override
    public String getValue() {
        return ((RadioButton)((MainActivity)MainActivity.context).findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
    }
}

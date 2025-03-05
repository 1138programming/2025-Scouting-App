package com.scouting_app_2025.UIElements;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.core.app.ActivityCompat;

import com.scouting_app_2025.MainActivity;
import com.scouting_app_2025.R;

import java.util.ArrayList;

public class Spinner extends UIElement {
    private final android.widget.Spinner spinner;
    private final boolean addOther;
    public Spinner(int datapointID, android.widget.Spinner spinner, boolean addOther) {
        super(datapointID);
        this.spinner = spinner;
        this.addOther = addOther;
        if(addOther) updateSpinnerList(new ArrayList<>());
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clicked();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner.setSelection(0);
            }
        });
    }

    @Override
    public void clicked() {
        super.clicked();
    }

    @Override
    public String getValue() {
        return spinner.getSelectedItem().toString();
    }

    public void updateSpinnerList(ArrayList<CharSequence> providedList) {
        ArrayList<CharSequence> spinnerList = new ArrayList<>();
        if(addOther) spinnerList.add("Other");
        spinnerList.addAll(providedList);
        ArrayAdapter<CharSequence> listAdapter
                = new ArrayAdapter<>(MainActivity.context, R.layout.spinner_layout, spinnerList);
        listAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(listAdapter);
    }
}

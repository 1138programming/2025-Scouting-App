package com.scouting_app_2025.UIElements;

import android.content.res.ColorStateList;

import java.util.Objects;

public class ImageButton extends UIElement {
    private final android.widget.ImageButton binding;
    private int color;

    /**
     * This constructor is used to create an independent button and takes a
     * binding since it's the only datapoint for the given button binding. It
     * doesn't take an UndoStack as it doesn't track data and is for UI purposes
     * only.
     *
     * @param datapointID datapointID of the button (should be negative given that
     *                    the button doesn't store data)
     * @param binding binding of the button
     */
    public ImageButton(int datapointID, android.widget.ImageButton binding) {
        super(datapointID);
        this.binding = binding;
        this.color = Objects.requireNonNull(binding.getBackgroundTintList()).getDefaultColor();
        binding.setOnClickListener(view -> clicked());
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        if(binding != null) {
            binding.setBackgroundTintList(ColorStateList.valueOf(this.color));
        }
    }
}

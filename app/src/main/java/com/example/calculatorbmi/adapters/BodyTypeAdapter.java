package com.example.calculatorbmi.adapters;

import android.view.View;
import android.widget.AdapterView;

public class BodyTypeAdapter implements AdapterView.OnItemSelectedListener {

    public static final String[] bodyTypes = {"Small", "Medium", "Large"};
    private String selected;

    public BodyTypeAdapter() {
        selected = "";
    }

    public String getSelected() {
        return selected;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = bodyTypes[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

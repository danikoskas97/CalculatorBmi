package com.example.calculatorbmi.adapters;

import android.view.View;
import android.widget.AdapterView;

public class SexAdapter implements AdapterView.OnItemSelectedListener {

    public static final String[] sex = {"Male", "Female"};
    private String selected;

    public SexAdapter() {
        selected = "";
    }

    public String getSelected() {
        return selected;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = sex[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

package com.example.calculatorbmi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.calculatorbmi.adapters.BodyTypeAdapter;
import com.example.calculatorbmi.adapters.SexAdapter;

import java.io.IOException;

import static com.example.calculatorbmi.ResultsActivity.*;
import static com.example.calculatorbmi.adapters.BodyTypeAdapter.bodyTypes;
import static com.example.calculatorbmi.adapters.SexAdapter.sex;

public class MainActivity extends AppCompatActivity {

    private BodyTypeAdapter bodyTypeAdapter;
    private SexAdapter sexAdapter;
    private EditText firstName;
    private EditText lastName;
    private EditText age;
    private EditText weight;
    private TextView heightProgress;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        height = 0;
        initXMLComponents();
    }

    private void initXMLComponents() {
        bodyTypeAdapter = new BodyTypeAdapter();
        sexAdapter = new SexAdapter();
        setSpinner(R.id.body_type, bodyTypes, bodyTypeAdapter);
        setSpinner(R.id.sex, sex, sexAdapter);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        age = findViewById(R.id.age);
        weight = findViewById(R.id.weight);
        heightProgress = findViewById(R.id.height_progress);
        SeekBar heightSeekBar = findViewById(R.id.height_slider);
        heightSeekBar.setMax(50);
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                height = progress + 140;
                heightProgress.setText("Height: " + height);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
            Button submitButton = findViewById(R.id.submit_button);
            submitButton.setOnClickListener(this::threadedSubmit);
    }

    private void setSpinner(int id, String[] options, AdapterView.OnItemSelectedListener adapter) {
        Spinner bodyType = findViewById(id);
        bodyType.setOnItemSelectedListener(adapter);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodyType.setAdapter(aa);
    }

    public void threadedSubmit(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                submit(view);
            }
        }).start();
    }

    public void submit(View view) {
        String firstName = this.firstName.getText().toString();
        String lastName = this.lastName.getText().toString();
        String age = this.age.getText().toString();
        String weight = this.weight.getText().toString();
        String bodyType = bodyTypeAdapter.getSelected();
        String sex = sexAdapter.getSelected();
        if(isMissingField(firstName,
                lastName,
                age,
                height + "",
                weight,
                bodyType,
                sex)) {
            Toast.makeText(this, "Please set all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        double bmi = 0;
        try {
            bmi = BMICalculator.calcBMI(toDouble(height + ""), toDouble(weight));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent resultsIntent = new Intent(this, ResultsActivity.class);
        resultsIntent.putExtra(USER_NAME_AND_SEX, String.format("%s %s (%s)", firstName, lastName, sex));
        resultsIntent.putExtra(BMI, bmi);
        resultsIntent.putExtra(WEIGHT, toDouble(weight));
        try {
            resultsIntent.putExtra(WEIGHT_STATUS, BMICalculator.getWeightStatus(bmi));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            resultsIntent.putExtra(IDEAL_WEIGHT, BMICalculator.getIdealWeight(toDouble(height + ""), Integer.parseInt(age), bodyType));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        startActivity(resultsIntent);
    }

    private double toDouble(String stringVal) {
        return Double.parseDouble(stringVal);
    }

    private boolean isMissingField(String firstName, String lastName, String age, String height, String weight, String bodyType, String sex) {
        return firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty() || bodyType.isEmpty() || sex.isEmpty();
    }

}
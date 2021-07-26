package com.example.calculatorbmi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static java.lang.String.*;

public class ResultsActivity extends AppCompatActivity {

    public static final String USER_NAME_AND_SEX = "uid";
    public static final String BMI = "bmi";
    public static final String WEIGHT_STATUS = "weightStatus";
    public static final String IDEAL_WEIGHT = "idealWeight";
    public static final String WEIGHT = "Weight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();

        String userName = intent.getExtras().getString(USER_NAME_AND_SEX);
        double bmi = intent.getExtras().getDouble(BMI);
        String weightStatus = intent.getExtras().getString(WEIGHT_STATUS);
        double weight = intent.getExtras().getDouble(WEIGHT);
        double idealWeight = intent.getExtras().getDouble(IDEAL_WEIGHT);

        TextView userNameDisplay = findViewById(R.id.user_name);
        TextView bmiDisplay = findViewById(R.id.bmi);
        TextView weightStatusDisplay = findViewById(R.id.weight_status);
        TextView idealWeightDisplay = findViewById(R.id.ideal_weight);

        userNameDisplay.setText(userName);
        bmiDisplay.setText(format("BMI: %.2f", bmi));
        weightStatusDisplay.setText(format("Weight Status: %s", weightStatus));
        idealWeightDisplay.setText(format("Your Weight: %.2f%nIdeal Weight: %.2f", weight, idealWeight));
    }
}
package com.calculator.lab1.calculatorsimpleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void calculateSimple(View view) {
        Intent intent = new Intent(this, SimpleCalcActivity.class);
        startActivity(intent);
    }

    public void calculateScientific(View view) {
        Intent intent = new Intent(this, ScientificCalcActivity.class);
        startActivity(intent);
    }

    public void about(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}

package com.example.se2_einzelbeispiel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalculateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String mnr = extra.getString("mnr");
            TextView textViewMNr = (TextView) findViewById(R.id.textViewMNr);
            textViewMNr.setText(mnr);
        }

        //Aufgabe 2
        
    }
}

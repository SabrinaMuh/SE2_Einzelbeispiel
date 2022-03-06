package com.example.se2_einzelbeispiel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
            Button buttonCalculate = (Button) findViewById(R.id.buttonCalculate);
            buttonCalculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculate(mnr);
                }
            });
        }
    }

    private void calculate(String mnr){
        TextView index1 = (TextView) findViewById(R.id.textViewIndex1);
        TextView index2 = (TextView) findViewById(R.id.textViewIndex2);
        boolean found = false;
        for (int i = 0; i < mnr.length(); i++) {
            if(found) break;
            int value1 = Character.getNumericValue(mnr.charAt(i));
            for (int j = i+1; j < mnr.length(); j++){
                int value2 = Character.getNumericValue(mnr.charAt(j));
                int divider = ggt(value1, value2);
                if(divider > 1) {
                    index1.append(" " + i);
                    index2.append(" " + j);
                    found = true;
                    break;
                }
            }
        }
    }

    private int ggt(int value1, int value2){
        if(value1 == 0) return value2;
        while (value2 != 0) {
            if(value1 > value2) value1 = value1 - value2;
            else value2 = value2 - value1;
        }
        return value1;
    }
}

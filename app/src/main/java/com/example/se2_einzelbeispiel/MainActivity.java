package com.example.se2_einzelbeispiel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class MatrikelnrThread extends Thread{
    String mnr;
    String answer;

    public MatrikelnrThread(String mnr){
        this.mnr = mnr;
    }

    @Override
    public void run(){
        try {
            Socket socket = new Socket("se2-isys.aau.at", 53212);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeBytes(mnr + "\n");

            answer = inputReader.readLine();

            socket.close();
        }catch (Exception e){
            answer = "ERROR: crashed";
        }
    }
}

public class MainActivity extends AppCompatActivity {
    EditText mnrText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity();
            }
        });

        Button buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnrText = (EditText) findViewById(R.id.editTextMatrikelNR);
                TextView answer = (TextView) findViewById(R.id.textViewAnswer);
                String mnr = mnrText.getText().toString();

                MatrikelnrThread thread = new MatrikelnrThread(mnr);

                thread.start();

                try{
                    thread.join();
                }catch (InterruptedException ie){

                }
                answer.setText(thread.answer);

                if(!answer.getText().equals("Dies ist keine gueltige Matrikelnummer")) buttonNext.setVisibility(View.VISIBLE);
            }
        });
    }

    private void switchActivity(){
        Intent intent = new Intent(this, CalculateActivity.class);
        intent.putExtra("mnr", mnrText.getText().toString());
        startActivity(intent);
    }
}
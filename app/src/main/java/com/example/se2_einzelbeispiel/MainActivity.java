package com.example.se2_einzelbeispiel;

import androidx.appcompat.app.AppCompatActivity;

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

            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeBytes(mnr + "\n");

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            answer = inputReader.readLine();

            socket.close();
        }catch (Exception e){
            answer = "ERROR: crashed";
        }
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mnrText = (EditText) findViewById(R.id.editTextMatrikelNR);
                TextView answer = (TextView) findViewById(R.id.textViewAnswer);
                String mnr = mnrText.getText().toString();

                MatrikelnrThread thread = new MatrikelnrThread(mnr);

                thread.start();

                try{
                    thread.join();
                }catch (InterruptedException ie){

                }
                answer.setText(thread.answer);
            }
        });
    }
}
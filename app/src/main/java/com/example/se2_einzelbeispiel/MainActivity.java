package com.example.se2_einzelbeispiel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.DataOutputStream;
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
    }
}
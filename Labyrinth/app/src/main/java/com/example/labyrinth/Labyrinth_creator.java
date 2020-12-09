package com.example.labyrinth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Labyrinth_creator extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinth_creator);

        button1 = (Button) findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        button2 = (Button) findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });


        button3 = (Button) findViewById(R.id.button5);
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });


    }
}
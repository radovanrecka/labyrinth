package com.example.layrinth_new;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class Labyrinth_creator extends AppCompatActivity {

    Labyrinth labyrinth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_labyrinth_creator);
        labyrinth = new Labyrinth(this);
        labyrinth.setBackgroundColor(Color.GREEN);
        setContentView(labyrinth);
    }

}
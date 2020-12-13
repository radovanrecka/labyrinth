package com.example.layrinth_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Labyrinth_creator extends AppCompatActivity {

    Labyrinth labyrinth;
    public static int highsScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_labyrinth_creator);
        //Vykreslení pozadí
        labyrinth = new Labyrinth(this);
        labyrinth.setBackgroundColor(Color.GREEN);
        setContentView(labyrinth);
        //Spuštění hudby na pozadí
        MediaPlayer player = MediaPlayer.create(Labyrinth_creator.this, R.raw.song);
        player.setLooping(true);
        player.start();
    }

/*
        SharedPreferences sh = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
        highsScore = sh.getInt("HIGH",0);
        if(Labyrinth.score >= highsScore){


            SharedPreferences.Editor editor = sh.edit();
            editor.putInt("HIGH", Labyrinth.score);
            editor.commit();
        }
        SharedPreferences sp = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
        highsScore = sp.getInt("HIGH", 0);




    public void play(){
        MediaPlayer player = MediaPlayer.create(Labyrinth_creator.this, R.raw.song);
        player.setLooping(true);
        player.start();
    }
    */
    //Ukládání highScore do SharedPrefernces
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sh = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
        //highsScore = sh.getInt("HIGH",0);
        if(Labyrinth.score >= highsScore){


            SharedPreferences.Editor editor = sh.edit();
            editor.putInt("HIGH", Labyrinth.score);
            editor.commit();
        }
        SharedPreferences sp = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
        highsScore = sp.getInt("HIGH", 0);
    }



}
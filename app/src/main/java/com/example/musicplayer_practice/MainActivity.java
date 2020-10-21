package com.example.musicplayer_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

//    MediaPlayer musicplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        musicplayer = MediaPlayer.create(this,R.raw.yen);

    }

    public void playmusic(View view) {

        Intent startIntent = new Intent(this,MusicService.class);
        startIntent.setAction("com.example.foregroundservice.action.startforeground");
        startService(startIntent);
//        musicplayer.start();



    }

    public void stopmusic(View view) {
//        musicplayer.stop();
//        musicplayer = MediaPlayer.create(this,R.raw.yen);
        Intent stopIntent = new Intent(this,MusicService.class);
        stopIntent.setAction("com.example.foregroundservice.action.stopforeground");
        stopService(stopIntent);


    }
}
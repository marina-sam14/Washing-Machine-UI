package com.example.washingmachine;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Cancelled extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelled);

        Button door = (Button) findViewById(R.id.cancelled);
        Button home_page = (Button) findViewById(R.id.home_page);

        home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cancelled.this, MainActivity.class);
                startActivity(intent);
//                Snackbar.make(v, "ΘΕΣΑΤΕ ΤΗΝ ΠΛΥΣΗ ΣΕ ΠΑΥΣΗ ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
            }


        });

        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "ΠΕΡΙΜΕΝΕΤΕ ΝΑ ΑΚΟΥΣΕΤΕ ΤΟΝ ΗΧΟ ΤΗΣ ΠΟΡΤΑΣ ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
//                SystemClock.sleep(5000);
                MediaPlayer music = MediaPlayer.create(Cancelled.this, R.raw.door);
                music.start();

            }


        });
    }
}

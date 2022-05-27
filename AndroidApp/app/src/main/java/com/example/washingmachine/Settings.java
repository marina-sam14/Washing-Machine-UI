package com.example.washingmachine;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Settings extends AppCompatActivity {

    boolean voiceOn;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        voiceOn = getIntent().getBooleanExtra("VOICE_ON", false);

        TextView date = findViewById(R.id.date);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        ImageButton home = (ImageButton) findViewById(R.id.homepage);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(Settings.this, R.raw.return_to_home);
                    music.start();
                }
                Intent intent = new Intent(Settings.this, MainActivity.class);
                intent.putExtra("VOICE_ON", voiceOn);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}

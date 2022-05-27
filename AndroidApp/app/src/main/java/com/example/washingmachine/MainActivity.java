package com.example.washingmachine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    boolean voiceOn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        voiceOn = getIntent().getBooleanExtra("VOICE_ON", false);

        Button programs = findViewById(R.id.programs);
        Button settings = findViewById(R.id.settings);
        Button door = findViewById(R.id.door);
        ImageButton voice = findViewById(R.id.voice);
        TextView date = findViewById(R.id.date);

        if (voiceOn) {
            voice.setImageDrawable(getDrawable(R.drawable.ic_baseline_hearing_disabled_24));
        } else {
            voice.setImageDrawable(getDrawable(R.drawable.ic_baseline_hearing_24));
        }

        voice.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if (!voiceOn) {
                    voiceOn = true;
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.enabled_voice_help);
                        music.start();
                    }
                    Snackbar.make(view, "ΕΝΕΡΓΟΠΟΙΗΣΑΤΕ ΤΗΝ ΦΩΝΗΤΙΚΗ ΥΠΟΒΟΗΘΗΣΗ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    voice.setImageDrawable(getDrawable(R.drawable.ic_baseline_hearing_disabled_24));
                } else {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.disabled_voice_help);
                        music.start();
                    }
                    voiceOn = false;
                    Snackbar.make(view, "ΑΠΕΝΕΡΓΟΠΟΙΗΣΑΤΕ ΤΗΝ ΦΩΝΗΤΙΚΗ ΥΠΟΒΟΗΘΗΣΗ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    voice.setImageDrawable(getDrawable(R.drawable.ic_baseline_hearing_24));
                }
            }
        });


        programs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.select_program);
                    music.start();
                }
                Intent intent = new Intent(MainActivity.this,FirstFragment.class);
                intent.putExtra("VOICE_ON", voiceOn);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.settings);
                    music.start();
                }
                Intent intent = new Intent(MainActivity.this,Settings.class);
                intent.putExtra("VOICE_ON", voiceOn);
                startActivity(intent);
            }
        });

        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.opening_door);
                    music.start();
                }
                Snackbar.make(v, "ΠΕΡΙΜΕΝΕΤΕ ΝΑ ΑΚΟΥΣΕΤΕ ΤΟΝ ΗΧΟ ΤΗΣ ΠΟΡΤΑΣ ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.door);
                music.start();
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);
    }
}
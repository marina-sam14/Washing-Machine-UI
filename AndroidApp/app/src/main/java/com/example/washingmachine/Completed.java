package com.example.washingmachine;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Completed extends AppCompatActivity {

    boolean voiceOn;

    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.completed);

        TextView date = findViewById(R.id.date);
        TextView clock = findViewById(R.id.clock);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        clock.setText(currentTime);

        voiceOn = getIntent().getBooleanExtra("VOICE_ON", false);

        Button home_page = (Button) findViewById(R.id.home_page);
        Button door = (Button) findViewById(R.id.door);

        home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(Completed.this, R.raw.return_to_home);
                    music.start();
                }
                Intent intent = new Intent(Completed.this, MainActivity.class);
                intent.putExtra("VOICE_ON", voiceOn);
                startActivity(intent);
            }
        });

        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(Completed.this, R.raw.opening_door);
                    music.start();
                }
                Snackbar.make(v, "ΠΕΡΙΜΕΝΕΤΕ ΝΑ ΑΚΟΥΣΕΤΕ ΤΟΝ ΗΧΟ ΤΗΣ ΠΟΡΤΑΣ ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                MediaPlayer music = MediaPlayer.create(Completed.this, R.raw.door);
                music.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}

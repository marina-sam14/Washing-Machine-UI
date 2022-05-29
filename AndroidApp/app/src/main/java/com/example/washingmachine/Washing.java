package com.example.washingmachine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Washing extends AppCompatActivity {

    boolean paused = false;
    private int status;
    Handler handler = new Handler();
    boolean pressedCancel = false;
    boolean pressedPaused = false;
    boolean pressedContinue = false;
    boolean voiceOn;


    protected void onCreate(Bundle savedInstanceState) {

        // Window without namebar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.washing);

        // Receiving variables, time,wash text and voice message
        String text = getIntent().getStringExtra("WASH_TEXT");
        int tot = getIntent().getIntExtra("TOTAL_TIME", 42);
        status = getIntent().getIntExtra("TOTAL_PROGRESS", 5);
        voiceOn = getIntent().getBooleanExtra("VOICE_ON", false);

//        TextView with user's program choices
        TextView workTxt = (TextView) findViewById(R.id.working);
        TextView info = (TextView) findViewById(R.id.info);
        info.setText(text);

        TextView estimate = (TextView) findViewById(R.id.remainder);
        String finalTxt = "ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: " + tot + "'";
        estimate.setText(finalTxt);

        //        Setting date and time in the divider
        TextView date = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        // Setting progress bar
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.getLayoutParams().height = 200;

        Button pauseBtn = (Button) findViewById(R.id.continued);
        Button cancelBtn = (Button) findViewById(R.id.door);

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
//                We did not press the pause button so laundering is working
                if (paused) {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(Washing.this, R.raw.continue_wash);
                        music.start();
                    }
                    pressedPaused = false;
                    pressedContinue = true;
                    SpannableString content = new SpannableString("ΛΕΙΤΟΥΡΓΙΑ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: " + tot + "'");
                    pauseBtn.setText("ΠΑΥΣΗ");
                    progressBar.setVisibility(View.VISIBLE);
                    pauseBtn.setBackgroundColor(Color.GRAY);
                    paused = false;

                } else {
//                    We pressed the pause button
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(Washing.this, R.raw.pause_wash);
                        music.start();
                    }
                    pressedPaused = true;
                    pressedContinue = false;
                    SpannableString content = new SpannableString("ΠΑΥΣΗ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: ΠΑΥΣΗ");
                    pauseBtn.setText("ΣΥΝΕΧΙΣΗ ΠΛΥΣΗΣ");
                    progressBar.setVisibility(View.INVISIBLE);
                    pauseBtn.setBackgroundColor(Color.parseColor("#05be70"));
//                    We want to press the button many times that's why we set paused as true
                    paused = true;
                }
            }
        });

//        Canceling washing
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressedCancel=true;
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(Washing.this, R.raw.cancel_wash);
                    music.start();
                }
                Intent intent = new Intent(Washing.this, PopUpCanceling.class);
//                We want to save the progress bar's position to send it to PopUpCanceling fragment because if the user chooses to return in this fragment, the position must be the same
                intent.putExtra("TOTAL_TIME", tot);
                intent.putExtra("TOTAL_PROGRESS", progressBar.getProgress());
                intent.putExtra("VOICE_ON", voiceOn);
                startActivity(intent);
            }
        });



//        The progress bar's status runs as a thread until is equal to 100
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < 100 && !pressedCancel) {
                    status++;
                    android.os.SystemClock.sleep(50);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(status);
                        }
                    });
//                  if the progress bar is full, the laundering is completed successfully
                    if (status == 100) {
                        Intent intent = new Intent(Washing.this, Completed.class);
                        intent.putExtra("VOICE_ON", voiceOn);
                        startActivity(intent);
                    }
//                    if we have pressed pause, the progress bar's position will not change until we continue the laundering
                    while (pressedPaused) {
                        progressBar.setProgress(progressBar.getProgress());
                    }
                }
            }
        });
        thread.start();
    }

    @Override
//    We cannot use the back button that Android offers
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
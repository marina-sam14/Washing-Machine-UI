package com.example.washingmachine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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

public class Laundring extends AppCompatActivity {

    boolean paused = false;
    private int status;
    Handler handler = new Handler();
    boolean pressedCancel = false;
    boolean pressedPaused = false;
    boolean pressedContinue = false;


    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.washing);

        int tot = getIntent().getIntExtra("TOTAL_TIME", 42);
        status = getIntent().getIntExtra("TOTAL_PROGRESS", 5);

        TextView workTxt = (TextView) findViewById(R.id.working);
        TextView info = (TextView) findViewById(R.id.info);

        TextView estimate = (TextView) findViewById(R.id.remainder);
        String finalTxt = "ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: " + tot + "'";
        estimate.setText(finalTxt);


        TextView date = findViewById(R.id.date);
        TextView clock = findViewById(R.id.clock);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        clock.setText(currentTime);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.getLayoutParams().height = 200;
//        progressBar.invalidate();
        Button pauseBtn = (Button) findViewById(R.id.continued);
        Button cancelBtn = (Button) findViewById(R.id.door);


        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (paused) {
                    pressedPaused=false;
                    pressedContinue=true;
                    SpannableString content = new SpannableString("ΛΕΙΤΟΥΡΓΙΑ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: " + tot + "'");
                    pauseBtn.setText("ΠΑΥΣΗ");
                    progressBar.setVisibility(View.VISIBLE);
                    pauseBtn.setBackgroundColor(Color.GRAY);
                    paused = false;

                } else {
                    pressedPaused=true;
                    pressedContinue=false;
                    SpannableString content = new SpannableString("ΠΑΥΣΗ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: ΠΑΥΣΗ");
                    pauseBtn.setText("ΣΥΝΕΧΙΣΗ ΠΛΥΣΗΣ");
                    progressBar.setVisibility(View.INVISIBLE);
                    pauseBtn.setBackgroundColor(Color.parseColor("#00A300"));
                    paused = true;
                    String text = getIntent().getStringExtra("WASH_TEXT");
                    int tot = getIntent().getIntExtra("TOTAL_TIME", 42);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressedCancel=true;
                Intent intent = new Intent(Laundring.this, PopUpCanceling.class);
                intent.putExtra("TOTAL_TIME", tot);
                intent.putExtra("TOTAL_PROGRESS", progressBar.getProgress());
                startActivity(intent);
            }
        });

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
                    if (status == 100) {
                        Intent intent = new Intent(Laundring.this, Completed.class);
                        startActivity(intent);
                    }
                    while (pressedPaused) {
                        progressBar.setProgress(progressBar.getProgress());
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    public int getStatus(){
        return status;
    }
}
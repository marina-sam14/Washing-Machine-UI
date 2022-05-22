package com.example.washingmachine;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

public class Laundring extends AppCompatActivity {

    boolean paused = false;
    private int status=0;
    Handler handler = new Handler();


    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.laundrying);


        String text = getIntent().getStringExtra("WASH_TEXT");
        int tot = getIntent().getIntExtra("TOTAL_TIME", 42);

        TextView workTxt = (TextView) findViewById(R.id.working);
        TextView info = (TextView) findViewById(R.id.info);
        info.setText(text);
        TextView estimate = (TextView) findViewById(R.id.remainder);
        String finalTxt = "ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: " + tot + "'";
        estimate.setText(finalTxt);

        TextView date = (TextView) findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        TextView tClock = (TextView) findViewById(R.id.clock);
        String clock = tClock.getText().toString();
        tClock.setText(clock);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < 100){
                    status++;
                    android.os.SystemClock.sleep(500);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(status);
                        }
                    });
                }
                Intent intent = new Intent(Laundring.this,Completed.class);
                startActivity(intent);
            }
        }).start();




        Button pauseBtn = (Button) findViewById(R.id.continued);
        Button cancelBtn = (Button) findViewById(R.id.cancelled);

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (paused) {
                    SpannableString content = new SpannableString("ΛΕΙΤΟΥΡΓΙΑ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: " + tot + "'");
                    pauseBtn.setText("ΠΑΥΣΗ");
                    progressBar.setVisibility(View.VISIBLE);
                    pauseBtn.setBackgroundColor(Color.GRAY);


                } else {
                    SpannableString content = new SpannableString("ΠΑΥΣΗ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: ΠΑΥΣΗ");
                    pauseBtn.setText("ΣΥΝΕΧΙΣΗ ΠΛΥΣΗΣ");
//                    animator.pause();
                    progressBar.setVisibility(View.INVISIBLE);
                    pauseBtn.setBackgroundColor(Color.parseColor("#81c639"));
                    paused = true;
                }
            }
        });





        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Laundring.this, PopUpCanceling.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
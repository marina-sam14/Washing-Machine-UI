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

public class Laundring extends AppCompatActivity {

    boolean paused = false;
    private int status=0;
    Handler handler = new Handler();
    boolean pressedCancel = false;
    boolean pressedPaused = false;
    boolean pressedContinue = false;




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
        progressBar.getProgressDrawable().setColorFilter(
                Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.getLayoutParams().height = 200;
//        progressBar.invalidate();
        Button pauseBtn = (Button) findViewById(R.id.continued);
        Button cancelBtn = (Button) findViewById(R.id.door);


        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (paused) {
                    pressedPaused=true;
                    pressedContinue=false;
                    SpannableString content = new SpannableString("ΛΕΙΤΟΥΡΓΙΑ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: " + tot + "'");
                    pauseBtn.setText("ΠΑΥΣΗ");
                    progressBar.setVisibility(View.VISIBLE);
                    pauseBtn.setBackgroundColor(Color.GRAY);



                } else {
                    pressedPaused=false;
                    pressedContinue=true;
                    SpannableString content = new SpannableString("ΠΑΥΣΗ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: ΠΑΥΣΗ");
                    pauseBtn.setText("ΣΥΝΕΧΙΣΗ ΠΛΥΣΗΣ");
//                    animator.pause();
                    progressBar.setVisibility(View.INVISIBLE);
                    pauseBtn.setBackgroundColor(Color.parseColor("#81c639"));
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
                startActivity(intent);



            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < 100 && !pressedCancel) {
                    if (!pressedContinue) {
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
                    }else if (!pressedPaused) {
                        try {
                            status = getStatus();
                            progressBar.setProgress(getStatus());
                            if (status == 100) {
                                Thread.currentThread().wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (pressedCancel){
                        try {
                            status = getStatus();
                            progressBar.setProgress(getStatus());
                            if (status == 100) {
                                Thread.currentThread().wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
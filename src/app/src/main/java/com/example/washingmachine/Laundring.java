package com.example.washingmachine;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.laundrying);


        String text = getIntent().getStringExtra("WASH_TEXT");
        int tot = getIntent().getIntExtra("TOTAL_TIME", 42);

        TextView workTxt = (TextView) findViewById(R.id.working);
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
        Drawable progressDrawable = progressBar.getIndeterminateDrawable().mutate();
        progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgressDrawable(progressDrawable);
        progressBar.setIndeterminate(true);
        progressBar.setProgressTintList((ColorStateList.valueOf(Color.RED)));
        ValueAnimator animator = ValueAnimator.ofInt(0, progressBar.getMax());
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                progressBar.setProgress((Integer)animation.getAnimatedValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animator.start();

        Button pauseBtn = (Button) findViewById(R.id.continued);
        Button cancelBtn = (Button) findViewById(R.id.cancelled);

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paused) {
                    SpannableString content = new SpannableString("ΛΕΙΤΟΥΡΓΙΑ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: " + tot + "'");
                    pauseBtn.setText("ΠΑΥΣΗ");
                    animator.pause();
                    paused = false;
                }else {
                    SpannableString content = new SpannableString("ΠΑΥΣΗ");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    workTxt.setText(content);
                    estimate.setText("ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: ΠΑΥΣΗ");
                    pauseBtn.setText("ΣΥΝΕΧΙΣΗ ΠΛΥΣΗΣ");
                    animator.start();
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
package com.example.washingmachine;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Laundring extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laundrying);

        String text = getIntent().getStringExtra("WASH_TEXT");
        int tot = getIntent().getIntExtra("TOTAL_TIME", 42);

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
                // start your activity here
            }
        });
        animator.start();

        TextView header = (TextView) findViewById(R.id.textView4);
        header.setText(text);

        TextView estimate = (TextView) findViewById(R.id.textView9);
        String finalTxt = "ΕΚΤΙΜΩΜΕΝΟΣ ΧΡΟΝΟΣ ΟΛΟΚΛΗΡΩΣΗΣ: " + Integer.toString(tot) + "'";
        estimate.setText(finalTxt);

        Button paused = (Button) findViewById(R.id.continued);
        Button cancelled = (Button) findViewById(R.id.cancelled);
        paused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Laundring.this, Pause.class);
                startActivity(intent);
//                Snackbar.make(v, "ΘΕΣΑΤΕ ΤΗΝ ΠΛΥΣΗ ΣΕ ΠΑΥΣΗ ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                }


        });

        cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Laundring.this, PopUpCanceling.class);
                startActivity(intent);
//                Snackbar.make(v, "ΘΕΣΑΤΕ ΤΗΝ ΠΛΥΣΗ ΣΕ ΠΑΥΣΗ ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
            }


        });

    }
}
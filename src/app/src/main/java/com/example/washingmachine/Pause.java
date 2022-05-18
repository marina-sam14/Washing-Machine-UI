package com.example.washingmachine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Pause extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paused);


        Button continued = (Button) findViewById(R.id.continued);
        Button cancelled = (Button) findViewById(R.id.cancelled);
        continued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pause.this, Laundring.class);
                startActivity(intent);
//                Snackbar.make(v, "ΘΕΣΑΤΕ ΤΗΝ ΠΛΥΣΗ ΣΕ ΠΑΥΣΗ ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
            }


        });

        cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pause.this, PopUpCanceling.class);
                startActivity(intent);
//                Snackbar.make(v, "ΘΕΣΑΤΕ ΤΗΝ ΠΛΥΣΗ ΣΕ ΠΑΥΣΗ ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
            }


        });


    }
}

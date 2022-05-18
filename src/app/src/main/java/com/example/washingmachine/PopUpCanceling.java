package com.example.washingmachine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpCanceling extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_cancel);

    Button continued = (Button) findViewById(R.id.continued);
    Button cancel = (Button) findViewById(R.id.cancelled);

        continued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUpCanceling.this, Laundring.class);
                startActivity(intent);
//                Snackbar.make(v, "ΘΕΣΑΤΕ ΤΗΝ ΠΛΥΣΗ ΣΕ ΠΑΥΣΗ ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
            }


        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUpCanceling.this, Cancelled.class);
                startActivity(intent);
//                Snackbar.make(v, "ΘΕΣΑΤΕ ΤΗΝ ΠΛΥΣΗ ΣΕ ΠΑΥΣΗ ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
            }


        });

    }
}

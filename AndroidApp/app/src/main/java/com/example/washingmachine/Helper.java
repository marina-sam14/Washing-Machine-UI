package com.example.washingmachine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Helper extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

//        Window without namebar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.helper);

        ImageButton exit = (ImageButton) findViewById(R.id.exit);

//        X goes back to Program Choice menu
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Helper.this, FirstFragment.class);
                startActivity(intent);
            }
        });
    }
}

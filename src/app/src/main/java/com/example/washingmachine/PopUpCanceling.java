package com.example.washingmachine;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PopUpCanceling extends AppCompatActivity {
    boolean returnexists = false;
    boolean openexists = false;
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_cancel);

        Button continued = (Button) findViewById(R.id.continued);
        Button cancel = (Button) findViewById(R.id.door);

        ImageView image = (ImageView) findViewById(R.id.imageView3);
        TextView txt = (TextView) findViewById(R.id.text);

        TextView date = (TextView) findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        TextView tClock = (TextView) findViewById(R.id.clock);
        String clock = tClock.getText().toString();
        tClock.setText(clock);

        continued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (returnexists) {
                    Intent intent = new Intent(PopUpCanceling.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PopUpCanceling.this, Laundring.class);
                    startActivity(intent);
//                    intent.putExtra("WASH_TEXT", text);
//                    intent.putExtra("TOTAL_TIME", tot);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openexists) {
                    Snackbar.make(v, "ΠΕΡΙΜΕΝΕΤΕ ΝΑ ΑΚΟΥΣΕΤΕ ΤΟΝ ΗΧΟ ΤΗΣ ΠΟΡΤΑΣ ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    MediaPlayer music = MediaPlayer.create(PopUpCanceling.this, R.raw.door);
                    music.start();
                } else {
                    txt.setText("Η ΠΛΥΣΗ ΑΚΥΡΩΘΗΚΕ ΜΕ ΕΠΙΤΥΧΙΑ");
                    image.setImageResource(R.drawable.ic_wrong);
                    continued.setText("ΕΠΙΣΤΡΟΦΗ ΣΤΗΝ ΑΡΧΗ");
                    cancel.setText("ΑΝΟΙΓΜΑ ΠΟΡΤΑΣ ΠΛΥΝΤΗΡΙΟΥ");
                    cancel.setBackgroundTintList(getResources().getColorStateList(R.color.otherGreen));
                    continued.setBackgroundTintList(getResources().getColorStateList(R.color.myGreen));
                    returnexists = true;
                    openexists = true;
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}

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
import java.util.Date;
import java.util.Locale;

public class PopUpCanceling extends AppCompatActivity {

    boolean returnexists = false;
    boolean openexists = false;
    boolean voiceOn;

    protected void onCreate(Bundle savedInstanceState) {

        // Window without namebar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_cancel);

        // Receiving variables from the Washing class, time,wash text and voice message
        String text = getIntent().getStringExtra("WASH_TEXT");
        voiceOn = getIntent().getBooleanExtra("VOICE_ON", false);
        int tot = getIntent().getIntExtra("TOTAL_TIME", 42);
        int status = getIntent().getIntExtra("TOTAL_PROGRESS", 5);

        Button continued = (Button) findViewById(R.id.continued);
        Button cancel = (Button) findViewById(R.id.door);
        ImageView image = (ImageView) findViewById(R.id.imageView3);
        TextView txt = (TextView) findViewById(R.id.text);
        TextView date = findViewById(R.id.date);
        TextView clock = findViewById(R.id.clock);

        // Setting date and time in the divider
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        clock.setText(currentTime);

        // Back to washing fragment button
        continued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (returnexists) {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(PopUpCanceling.this, R.raw.return_to_home);
                        music.start();
                    }
                    Intent intent = new Intent(PopUpCanceling.this, MainActivity.class);
                    intent.putExtra("VOICE_ON", voiceOn);
                    startActivity(intent);
                } else {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(PopUpCanceling.this, R.raw.return_to_wash);
                        music.start();
                    }
                    Intent intent = new Intent(PopUpCanceling.this, Washing.class);
                    // When we return to previous fragment, time and progress bar should be in the same spot as it was before
                    intent.putExtra("TOTAL_TIME", tot);
                    intent.putExtra("WASH_TEXT", text);
                    intent.putExtra("TOTAL_PROGRESS", status);
                    intent.putExtra("VOICE_ON", voiceOn);
                    startActivity(intent);
                }
            }
        });

        // Canceling washing
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Button for the door to be opened
                if (openexists) {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(PopUpCanceling.this, R.raw.opening_door);
                        music.start();
                    }
                    Snackbar.make(v, "ΠΕΡΙΜΕΝΕΤΕ ΝΑ ΑΚΟΥΣΕΤΕ ΤΟΝ ΗΧΟ ΤΗΣ ΠΟΡΤΑΣ ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    MediaPlayer music = MediaPlayer.create(PopUpCanceling.this, R.raw.door);
                    music.start();
                } else {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(PopUpCanceling.this, R.raw.cancel_wash);
                        music.start();
                    }
//                    Confirmation message for the cancelled laundering
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
//    We cannot use the back button that Android offers
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}

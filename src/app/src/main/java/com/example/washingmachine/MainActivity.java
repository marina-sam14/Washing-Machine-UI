package com.example.washingmachine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NonNls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    SpeechRecognizer speechRecognizer;
    boolean voiceOn = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button programs = (Button)findViewById(R.id.programs);
        Button settings = (Button)findViewById(R.id.settings);
        Button door = (Button) findViewById(R.id.door);
        ImageButton voice = (ImageButton) findViewById(R.id.voice);

        TextView date = findViewById(R.id.date);
        TextView clock = findViewById(R.id.clock);
        TextView voicecom = findViewById(R.id.voicecommand);
        voicecom.setVisibility(View.GONE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    voiceOn = false;
                    voice.setImageResource(R.drawable.ic_baseline_mic);
                    Snackbar.make(view, "ΑΠΕΝΕΡΓΟΠΟΙΗΣΑΤΕ ΤΗΝ ΦΩΝΗΤΙΚΗ ΠΕΡΙΗΓΗΣΗ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    //voicecom.setVisibility(View.GONE);
                    //date.setVisibility(View.VISIBLE);
                    //clock.setVisibility(View.VISIBLE);
                    speechRecognizer.startListening(speechRecognizerIntent);
                } else {
                    voiceOn = true;
                    voice.setImageResource(R.drawable.ic_baseline_mic_off);
                    Snackbar.make(view, "ΕΝΕΡΓΟΠΟΙΗΣΑΤΕ ΤΗΝ ΦΩΝΗΤΙΚΗ ΠΕΡΙΗΓΗΣΗ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    voicecom.setVisibility(View.VISIBLE);
                    date.setVisibility(View.INVISIBLE);
                    clock.setVisibility(View.INVISIBLE);
                    speechRecognizer.stopListening();
                }
            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> data = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                voicecom.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        programs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FirstFragment.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "ΠΕΡΙΜΕΝΕΤΕ ΝΑ ΑΚΟΥΣΕΤΕ ΤΟΝ ΗΧΟ ΤΗΣ ΠΟΡΤΑΣ ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.door);
                music.start();
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        clock.setText(currentTime);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "ΑΠΑΙΤΟΥΝΤΑΙ ΔΙΚΑΙΩΜΑΤΑ ΗΧΟΥ ΓΙΑ ΤΗΝ ΕΝΕΡΓΟΠΟΙΗΣΗ ΤΩΝ ΦΩΝΗΤΙΚΩΝ ΛΕΙΤΟΥΡΓΙΩΝ", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
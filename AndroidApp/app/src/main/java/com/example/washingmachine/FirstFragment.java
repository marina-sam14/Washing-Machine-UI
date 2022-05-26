package com.example.washingmachine;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FirstFragment extends AppCompatActivity {

    boolean voiceOn;
    boolean normalb = false;
    boolean lightb = false;
    boolean strongb = false;

    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_first);

        voiceOn = getIntent().getBooleanExtra("VOICE_ON", false);

        Spinner spinnerTemperature = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTemperature.setAdapter(adapter);

        RadioButton normal = (RadioButton) findViewById(R.id.radio_normal);
        RadioButton light = (RadioButton) findViewById(R.id.radio_light);
        RadioButton strong = (RadioButton) findViewById(R.id.radio_strong);

        CheckBox favorite = (CheckBox) findViewById(R.id.btnfav);

        CheckBox extra1 = (CheckBox) findViewById(R.id.drybtn);
        CheckBox extra2 = (CheckBox) findViewById(R.id.washbtn);

        ImageButton start = (ImageButton) findViewById(R.id.nextbtn);
        TextView startTxt = (TextView) findViewById(R.id.next);
        ImageButton helper = (ImageButton) findViewById(R.id.helper);

        TextView date = findViewById(R.id.date);
        TextView clock = findViewById(R.id.clock);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        clock.setText(currentTime);


        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.favorite_program);
                    music.start();
                }
                // Check which checkbox was clicked
                if (checked) {
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΤΟ ΑΓΑΠΗΜΕΝΟ ΣΑΣ ΠΡΟΓΡΑΜΜΑ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    normal.setEnabled(false);
                    light.setEnabled(false);
                    strong.setEnabled(false);
                    extra1.setEnabled(false);
                    extra2.setEnabled(false);
                    normalb = false;
                    lightb = false;
                    strongb = false;
                } else {
                    normal.setEnabled(true);
                    light.setEnabled(true);
                    strong.setEnabled(true);
                    extra1.setEnabled(true);
                    extra2.setEnabled(true);
                    normalb = false;
                    lightb = false;
                    strongb = false;
                }
            }
        });

        extra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.extra_dry_fifteen);
                    music.start();
                }
                // Check which checkbox was clicked
                if (checked) {
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΣΤΥΨΙΜΟ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        extra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.extra_wash_twenty);
                    music.start();
                }
                // Check which checkbox was clicked
                if (checked) {
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΞΕΒΓΑΛΜΑ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.program_info);
                    music.start();
                }
                Intent intent = new Intent(FirstFragment.this, Helper.class);
                intent.putExtra("VOICE_ON", voiceOn);
                startActivity(intent);
            }
        });

        startTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!normalb && !lightb && !strongb && !favorite.isChecked()) {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.select_program_to_start);
                        music.start();
                    }
                    Snackbar.make(view, "ΠΡΕΠΕΙ ΝΑ ΕΠΙΛΕΞΕΤΕ ΠΡΟΓΡΑΜΜΑ ΓΙΑ ΝΑ ΞΕΚΙΝΗΣΕΤΕ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (favorite.isChecked()) {
                    String text = "ΚΑΝΟΝΙΚΟ 15' ΘΕΡΜΟΚΡΑΣΙΑ 30 ΞΕΒΓΑΛΜΑ ΣΤΥΨΙΜΟ";
                    int tot = 50;
                    Intent intent = new Intent(FirstFragment.this, Washing.class);
                    intent.putExtra("WASH_TEXT", text);
                    intent.putExtra("TOTAL_TIME", tot);
                    normalb = false;
                    lightb = false;
                    strongb = false;
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.starting_program);
                        music.start();
                    }
                    intent.putExtra("VOICE_ON", voiceOn);
                    startActivity(intent);
                } else {
                    if (normalb) {
                        String text = "ΚΑΝΟΝΙΚΟ ";
                        int tot = 15;
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
                        intent.putExtra("WASH_TEXT", text);
                        intent.putExtra("TOTAL_TIME", tot);
                        normalb = false;
                        lightb = false;
                        strongb = false;
                        if (voiceOn) {
                            MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.starting_program);
                            music.start();
                        }
                        intent.putExtra("VOICE_ON", voiceOn);
                        startActivity(intent);
                    } else if (lightb) {
                        String text = "ΕΛΑΦΡΥ ";
                        int tot = 60;
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
                        intent.putExtra("WASH_TEXT", text);
                        intent.putExtra("TOTAL_TIME", tot);
                        normalb = false;
                        lightb = false;
                        strongb = false;
                        if (voiceOn) {
                            MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.starting_program);
                            music.start();
                        }
                        intent.putExtra("VOICE_ON", voiceOn);
                        startActivity(intent);
                    } else if (strongb) {
                        String text = "ΙΣΧΥΡΟ ";
                        int tot = 60;
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
                        intent.putExtra("WASH_TEXT", text);
                        intent.putExtra("TOTAL_TIME", tot);
                        normalb = false;
                        lightb = false;
                        strongb = false;
                        if (voiceOn) {
                            MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.starting_program);
                            music.start();
                        }
                        intent.putExtra("VOICE_ON", voiceOn);
                        startActivity(intent);
                    }
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!normalb && !lightb && !strongb && !favorite.isChecked()) {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.select_program_to_start);
                        music.start();
                    }
                    Snackbar.make(view, "ΠΡΕΠΕΙ ΝΑ ΕΠΙΛΕΞΕΤΕ ΠΡΟΓΡΑΜΜΑ ΓΙΑ ΝΑ ΞΕΚΙΝΗΣΕΤΕ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (favorite.isChecked()) {
                    String text = "ΚΑΝΟΝΙΚΟ 15' ΘΕΡΜΟΚΡΑΣΙΑ 30 ΞΕΒΓΑΛΜΑ ΣΤΥΨΙΜΟ";
                    int tot = 50;
                    Intent intent = new Intent(FirstFragment.this, Washing.class);
                    intent.putExtra("WASH_TEXT", text);
                    intent.putExtra("TOTAL_TIME", tot);
                    normalb = false;
                    lightb = false;
                    strongb = false;
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.starting_program);
                        music.start();
                    }
                    intent.putExtra("VOICE_ON", voiceOn);
                    startActivity(intent);
                } else {
                    if (normalb) {
                        String text = "ΚΑΝΟΝΙΚΟ ";
                        int tot = 15;
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
                        intent.putExtra("WASH_TEXT", text);
                        intent.putExtra("TOTAL_TIME", tot);
                        normalb = false;
                        lightb = false;
                        strongb = false;
                        if (voiceOn) {
                            MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.starting_program);
                            music.start();
                        }
                        intent.putExtra("VOICE_ON", voiceOn);
                        startActivity(intent);
                    } else if (lightb) {
                        String text = "ΕΛΑΦΡΥ ";
                        int tot = 60;
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
                        intent.putExtra("WASH_TEXT", text);
                        intent.putExtra("TOTAL_TIME", tot);
                        normalb = false;
                        lightb = false;
                        strongb = false;
                        if (voiceOn) {
                            MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.starting_program);
                            music.start();
                        }
                        intent.putExtra("VOICE_ON", voiceOn);
                        startActivity(intent);
                    } else if (strongb) {
                        String text = "ΙΣΧΥΡΟ ";
                        int tot = 60;
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
                        intent.putExtra("WASH_TEXT", text);
                        intent.putExtra("TOTAL_TIME", tot);
                        normalb = false;
                        lightb = false;
                        strongb = false;
                        if (voiceOn) {
                            MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.starting_program);
                            music.start();
                        }
                        intent.putExtra("VOICE_ON", voiceOn);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_normal:
                if (checked)
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.normal_fifteen);
                        music.start();
                    }
                    normalb = true;
                    lightb = false;
                    strongb = false;
                    break;
            case R.id.radio_light:
                if (checked)
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.light_sixty);
                        music.start();
                    }
                    normalb = false;
                    lightb = true;
                    strongb = false;
                    break;
            case R.id.radio_strong:
                if (checked)
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.strong_sixty);
                        music.start();
                    }
                    normalb = false;
                    lightb = false;
                    strongb = true;
                    break;
        }
    }
}




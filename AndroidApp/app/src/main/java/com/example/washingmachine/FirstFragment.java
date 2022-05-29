package com.example.washingmachine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FirstFragment extends AppCompatActivity {

    boolean voiceOn;
    boolean normalb = false;
    boolean lightb = false;
    boolean strongb = false;
    boolean pressedSchedule = false;
    int mselectedDay, mselectedMonth, mselectedYear, mHour, mMinute, myHour, myMinute;


    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_first);

        voiceOn = getIntent().getBooleanExtra("VOICE_ON", false);

        Spinner spinnerTemperature = findViewById(R.id.spinner);
        String[] list = {"30 °C", "40 °C", "50 °C", "60 °C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTemperature.setPrompt("ΕΠΙΛΟΓΗ ΘΕΡΜΟΚΡΑΣΙΑΣ");
        spinnerTemperature.setAdapter(adapter);

        RadioButton normal = findViewById(R.id.radio_normal);
        RadioButton light = findViewById(R.id.radio_light);
        RadioButton strong = findViewById(R.id.radio_strong);

        CheckBox favorite = findViewById(R.id.btnfav);

        CheckBox extra1 = findViewById(R.id.drybtn);
        CheckBox extra2 = findViewById(R.id.washbtn);

        ImageButton start = findViewById(R.id.nextbtn);
        start.setVisibility(View.VISIBLE);
        TextView startTxt = findViewById(R.id.next);
        ImageButton helper = findViewById(R.id.helper);

        Button schedulingProgram = findViewById(R.id.scheduled);
        TextView datePicker = findViewById(R.id.datePicker);
        TextView timePicker = findViewById(R.id.timePicker);
        TextView scheduled_start = findViewById(R.id.scheduled_next);
        TextView cancel_scheduled = findViewById(R.id.cancel_schedule);
        datePicker.setVisibility(View.INVISIBLE);
        timePicker.setVisibility(View.INVISIBLE);
        scheduled_start.setVisibility(View.INVISIBLE);
        cancel_scheduled.setVisibility(View.INVISIBLE);

        TextView date = findViewById(R.id.date);
        TextView clock = findViewById(R.id.clock);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        clock.setText(currentTime);

        ImageButton home = (ImageButton) findViewById(R.id.homepage);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.return_to_home);
                    music.start();
                }
                Intent intent = new Intent(FirstFragment.this, MainActivity.class);
                intent.putExtra("VOICE_ON", voiceOn);
                startActivity(intent);
            }
        });

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
                }else if (pressedSchedule){
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.successfully_scheduling);
                        music.start();
                    }
                    Snackbar.make(view, "Η ΠΛΥΣΗ ΣΑΣ ΠΡΟΓΡΑΜΜΑΤΙΣΤΗΚΕ ΜΕ ΕΠΙΤΥΧΙΑ", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }
                else if (favorite.isChecked()) {
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


        schedulingProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int time[];
                    if (!normalb && !lightb && !strongb && !favorite.isChecked()) {
                        if (voiceOn) {
                            MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.select_program_to_start);
                            music.start();
                        }
                        Snackbar.make(view, "ΠΡΕΠΕΙ ΝΑ ΕΠΙΛΕΞΕΤΕ ΠΡΟΓΡΑΜΜΑ ΓΙΑ ΝΑ ΞΕΚΙΝΗΣΕΤΕ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (favorite.isChecked()) {

                        String text = "ΚΑΝΟΝΙΚΟ 15' ΘΕΡΜΟΚΡΑΣΙΑ 30 ΞΕΒΓΑΛΜΑ ΣΤΥΨΙΜΟ";
                        int tot = 50;
                        datePicker.setVisibility(View.VISIBLE);
                        timePicker.setVisibility(View.VISIBLE);
                        startTxt.setVisibility(View.INVISIBLE);
                        scheduled_start.setVisibility(View.VISIBLE);
                        cancel_scheduled.setVisibility(View.VISIBLE);
                        Calendar mcurrentDate = Calendar.getInstance();
                        int year = mcurrentDate.get(Calendar.YEAR);
                        int month = mcurrentDate.get(Calendar.MONTH);
                        int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog mDatePicker = new DatePickerDialog(FirstFragment.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                                // TODO Auto-generated method stub
                                /*      Your code   to get date and time    */
                                Log.e("ΕΠΙΛΕΞΑΤΕ ", selectedDay + "/ " + selectedMonth + " / " + selectedYear);
                                datePicker.setText("Η ΠΛΥΣΗ ΘΑ ΠΡΑΓΜΑΤΟΠΟΙΗΘΕΙ ΣΤΙΣ:" + selectedDay + "/" + selectedMonth + "/" + selectedYear);
                            }
                        }, year, month, day);
                        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        mDatePicker.setTitle("ΕΠΙΛΕΞΤΕ ΗΜΕΡΟΜΗΝΙΑ");
                        mDatePicker.show();

                        mHour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
                        mMinute = mcurrentDate.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(FirstFragment.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        timePicker.setText("ΚΑΙ ΩΡΑ " + hourOfDay + ":" + String.format("%02d", minute));
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                // This is hiding the "Cancel" button:
                                timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
                            }
                        });
                        timePickerDialog.show();


                        normalb = false;
                        lightb = false;
                        strongb = false;
                        if (voiceOn) {
                            MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.scheduling_date_time);
                            music.start();
                        }
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
                            datePicker.setVisibility(View.VISIBLE);
                            timePicker.setVisibility(View.VISIBLE);
                            startTxt.setVisibility(View.INVISIBLE);
                            scheduled_start.setVisibility(View.VISIBLE);
                            cancel_scheduled.setVisibility(View.VISIBLE);
                            Calendar mcurrentDate = Calendar.getInstance();
                            int year = mcurrentDate.get(Calendar.YEAR);
                            int month = mcurrentDate.get(Calendar.MONTH);
                            int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                            DatePickerDialog mDatePicker = new DatePickerDialog(FirstFragment.this, new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                                    // TODO Auto-generated method stub
                                    /*      Your code   to get date and time    */
                                    Log.e("ΕΠΙΛΕΞΑΤΕ ", selectedDay + "/ " + selectedMonth + " / " + selectedYear);
                                    datePicker.setText("Η ΠΛΥΣΗ ΘΑ ΠΡΑΓΜΑΤΟΠΟΙΗΘΕΙ ΣΤΙΣ:" + selectedDay + "/" + selectedMonth + "/" + selectedYear);
                                }
                            }, year, month, day);
                            mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                            mDatePicker.setTitle("ΕΠΙΛΕΞΤΕ ΗΜΕΡΟΜΗΝΙΑ");
                            mDatePicker.show();

                            mHour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
                            mMinute = mcurrentDate.get(Calendar.MINUTE);

                            TimePickerDialog timePickerDialog = new TimePickerDialog(FirstFragment.this,
                                    new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay,
                                                              int minute) {

                                            timePicker.setText("ΚΑΙ ΩΡΑ " + hourOfDay + ":" + minute);
                                        }
                                    }, mHour, mMinute, false);
                            timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialog) {
                                    // This is hiding the "Cancel" button:
                                    timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
                                }
                            });
                            timePickerDialog.show();

                            normalb = false;
                            lightb = false;
                            strongb = false;
                            if (voiceOn) {
                                MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.scheduling_date_time);
                                music.start();
                            }
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

                            datePicker.setVisibility(View.VISIBLE);
                            timePicker.setVisibility(View.VISIBLE);
                            startTxt.setVisibility(View.INVISIBLE);
                            scheduled_start.setVisibility(View.VISIBLE);
                            cancel_scheduled.setVisibility(View.VISIBLE);
                            Calendar mcurrentDate = Calendar.getInstance();
                            int year = mcurrentDate.get(Calendar.YEAR);
                            int month = mcurrentDate.get(Calendar.MONTH);
                            int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                            DatePickerDialog mDatePicker = new DatePickerDialog(FirstFragment.this, new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                                    // TODO Auto-generated method stub
                                    /*      Your code   to get date and time    */
                                    Log.e("ΕΠΙΛΕΞΑΤΕ ", selectedDay + "/ " + selectedMonth + " / " + selectedYear);
                                    datePicker.setText("Η ΠΛΥΣΗ ΘΑ ΠΡΑΓΜΑΤΟΠΟΙΗΘΕΙ ΣΤΙΣ:" + selectedDay + "/" + selectedMonth + "/" + selectedYear);
                                }
                            }, year, month, day);
                            mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                            mDatePicker.setTitle("ΕΠΙΛΕΞΤΕ ΗΜΕΡΟΜΗΝΙΑ");
                            mDatePicker.show();

                            mHour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
                            mMinute = mcurrentDate.get(Calendar.MINUTE);

                            TimePickerDialog timePickerDialog = new TimePickerDialog(FirstFragment.this,
                                    new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay,
                                                              int minute) {

                                            timePicker.setText("ΚΑΙ ΩΡΑ " + hourOfDay + ":" + minute);
                                        }
                                    }, mHour, mMinute, false);
                            timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialog) {
                                    // This is hiding the "Cancel" button:
                                    timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
                                }
                            });
                            timePickerDialog.show();

                            normalb = false;
                            lightb = false;
                            strongb = false;
                            if (voiceOn) {
                                MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.scheduling_date_time);
                                music.start();
                            }
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
                            datePicker.setVisibility(View.VISIBLE);
                            timePicker.setVisibility(View.VISIBLE);
                            startTxt.setVisibility(View.INVISIBLE);
                            scheduled_start.setVisibility(View.VISIBLE);
                            cancel_scheduled.setVisibility(View.VISIBLE);
                            Calendar mcurrentDate = Calendar.getInstance();
                            int year = mcurrentDate.get(Calendar.YEAR);
                            int month = mcurrentDate.get(Calendar.MONTH);
                            int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                            DatePickerDialog mDatePicker = new DatePickerDialog(FirstFragment.this, new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                                    // TODO Auto-generated method stub
                                    /*      Your code   to get date and time    */
                                    Log.e("ΕΠΙΛΕΞΑΤΕ ", selectedDay + "/ " + selectedMonth + " / " + selectedYear);
                                    datePicker.setText("Η ΠΛΥΣΗ ΘΑ ΠΡΑΓΜΑΤΟΠΟΙΗΘΕΙ ΣΤΙΣ:" + selectedDay + "/" + selectedMonth + "/" + selectedYear);
                                }
                            }, year, month, day);
                            mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                            mDatePicker.setTitle("ΕΠΙΛΕΞΤΕ ΗΜΕΡΟΜΗΝΙΑ");
                            mDatePicker.show();

                            mHour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
                            mMinute = mcurrentDate.get(Calendar.MINUTE);

                            TimePickerDialog timePickerDialog = new TimePickerDialog(FirstFragment.this,
                                    new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay,
                                                              int minute) {

                                            timePicker.setText("ΚΑΙ ΩΡΑ " + hourOfDay + ":" + minute);
                                        }
                                    }, mHour, mMinute, false);
                            timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialog) {
                                    // This is hiding the "Cancel" button:
                                    timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
                                }
                            });

                            timePickerDialog.show();

                            normalb = false;
                            lightb = false;
                            strongb = false;
                            if (voiceOn) {
                                MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.scheduling_date_time);
                                music.start();
                            }
                        }
                    }

            }


        });

        cancel_scheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.cancel_scheduled);
                    music.start();
                }
                Snackbar.make(view, "Η ΠΡΟΓΡΑΜΜΑΤΙΣΜΕΝΗ ΠΛΥΣΗ ΑΚΥΡΩΘΗΚΕ", Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show();
                Intent intent = new Intent(FirstFragment.this, FirstFragment.class);
                startActivity(intent);
            }
        });

        scheduled_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.successfully_scheduling);
                    music.start();
                }
                Snackbar.make(view, "Η ΠΛΥΣΗ ΣΑΣ ΠΡΟΓΡΑΜΜΑΤΙΣΤΗΚΕ ΜΕ ΕΠΙΤΥΧΙΑ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(FirstFragment.this, MainActivity.class);
                intent.putExtra("VOICE_ON", voiceOn);
                startActivity(intent);
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

    public int[] popTimePicker(View view) {
        int[] time = {};
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time[0] = selectedHour;
                time[1] = selectedMinute;
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        //TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, time[0], time[1], true);
        //timePickerDialog.setTitle("Select Time");
        //timePickerDialog.show();
        return time;
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}




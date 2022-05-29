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

        // Window without namebar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_first);

        voiceOn = getIntent().getBooleanExtra("VOICE_ON", false);

//        Temperature degrees spinner
        Spinner spinnerTemperature = findViewById(R.id.spinner);
        String[] list = {"30 °C", "40 °C", "60 °C", "90 °C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        spinnerTemperature.setPrompt("ΕΠΙΛΟΓΗ ΘΕΡΜΟΚΡΑΣΙΑΣ");
        spinnerTemperature.setAdapter(adapter);

        RadioButton normal = findViewById(R.id.radio_normal);
        RadioButton light = findViewById(R.id.radio_light);
        RadioButton strong = findViewById(R.id.radio_strong);

        CheckBox favorite = findViewById(R.id.btnfav);
        CheckBox extra1 = findViewById(R.id.drybtn);
        CheckBox extra2 = findViewById(R.id.washbtn);

//        Starting arrow image, visible when scheduling laundering is not enable
        ImageButton start = findViewById(R.id.nextbtn);
        start.setVisibility(View.VISIBLE);
//        Next step text
        TextView startTxt = findViewById(R.id.next);

//        Question mark image
        ImageButton helper = findViewById(R.id.helper);

//        Setting Scheduling laundering
        Button schedulingProgram = findViewById(R.id.scheduled);
        TextView datePicker = findViewById(R.id.datePicker);
        TextView timePicker = findViewById(R.id.timePicker);
//        Next step in Scheduling program
        TextView scheduled_start = findViewById(R.id.scheduled_next);
        TextView cancel_scheduled = findViewById(R.id.cancel_schedule);
//        scheduling stuff is invisible by default until user decides to enable the feature
        datePicker.setVisibility(View.INVISIBLE);
        timePicker.setVisibility(View.INVISIBLE);
        scheduled_start.setVisibility(View.INVISIBLE);
        cancel_scheduled.setVisibility(View.INVISIBLE);

        // Setting date and time in the divider
        TextView date = findViewById(R.id.date);
        TextView clock = findViewById(R.id.clock);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        clock.setText(currentTime);

        // back to home page
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

//        favourite program is checked
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
//                    when favourite is checked, all the other choices are not available
                    normal.setEnabled(false);
                    light.setEnabled(false);
                    strong.setEnabled(false);
                    extra1.setEnabled(false);
                    extra2.setEnabled(false);
//                    other programs are disabled
                    normalb = false;
                    lightb = false;
                    strongb = false;
                } else {
//                    if favourite is not checked, other choices are available
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

//        dry option is selected
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

//        extra wash option is checked
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

//        helper is pressed
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


//      starttxt is equal to next when Scheduling laundering is not checked
        startTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               User has to choose a program for the laundering to start, otherwise a message warns them to choose one
                if (!normalb && !lightb && !strongb && !favorite.isChecked()) {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.select_program_to_start);
                        music.start();
                    }
                    Snackbar.make(view, "ΠΡΕΠΕΙ ΝΑ ΕΠΙΛΕΞΕΤΕ ΠΡΟΓΡΑΜΜΑ ΓΙΑ ΝΑ ΞΕΚΙΝΗΣΕΤΕ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//               favourite program is checked
                } else if (favorite.isChecked()) {
                    String text = "ΚΑΝΟΝΙΚΟ 15' ΘΕΡΜΟΚΡΑΣΙΑ 30 ΞΕΒΓΑΛΜΑ ΣΤΥΨΙΜΟ";
                    int tot = 50;
                    Intent intent = new Intent(FirstFragment.this, Washing.class);
                    // Sending  time,wash text for the Washing TextView user's program choices
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
//                    Normal program is checked
                    if (normalb) {
                        String text = "ΚΑΝΟΝΙΚΟ ";
                        int tot = 15;
//                        Adding temperature degrees to the textview
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
//                        adding extra options to laundering and return the sum of the time duration of each choice
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
//                        Sending information to the next fragment
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
//                        Light program is checked
                    } else if (lightb) {
                        String text = "ΕΛΑΦΡΥ ";
                        int tot = 60;
                        // Adding temperature degrees to the textview
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
//                        adding extra options to laundering and return the sum of the time duration of each choice
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
//                        Sending information to the next fragment
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
//                        strong program is checked
                    } else if (strongb) {
                        String text = "ΙΣΧΥΡΟ ";
                        int tot = 60;
//                        Adding temperature degrees to the textview
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
//                        adding extra options to laundering and add the time duration of each choice
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
//                        Sending information to the next fragment
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


//      button for setting time and date to schedule the laundering
        schedulingProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                button is pressed
                pressedSchedule=true;
                    int time[];
                // User has to choose a program for the laundering to be scheduled, otherwise a message warns them to choose one
                    if (!normalb && !lightb && !strongb && !favorite.isChecked()) {
                        if (voiceOn) {
                            MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.select_program_to_start);
                            music.start();
                        }
                        Snackbar.make(view, "ΠΡΕΠΕΙ ΝΑ ΕΠΙΛΕΞΕΤΕ ΠΡΟΓΡΑΜΜΑ ΓΙΑ ΝΑ ΞΕΚΙΝΗΣΕΤΕ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
//                    favourite program is selected
                    else if (favorite.isChecked()) {
                        String text = "ΚΑΝΟΝΙΚΟ 15' ΘΕΡΜΟΚΡΑΣΙΑ 30 ΞΕΒΓΑΛΜΑ ΣΤΥΨΙΜΟ";
                        int tot = 50;
//                       only scheduling stuff should be visible
                        datePicker.setVisibility(View.VISIBLE);
                        timePicker.setVisibility(View.VISIBLE);
                        startTxt.setVisibility(View.INVISIBLE);
                        scheduled_start.setVisibility(View.VISIBLE);
                        cancel_scheduled.setVisibility(View.VISIBLE);

//                        setting DatePicker pop-up
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
//                      The first available date is today
                        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        mDatePicker.setTitle("ΕΠΙΛΕΞΤΕ ΗΜΕΡΟΜΗΝΙΑ");
                        mDatePicker.show();

                        // setting TimePicker pop-up
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
//                        We had to hide the cancel button in order to make sure that the user will not be able to skip the TimePicker choice
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
//                        normal program is selected
                        if (normalb) {
                            String text = "ΚΑΝΟΝΙΚΟ ";
                            int tot = 15;
//                            Adding temperature degrees to the textview
                            text += spinnerTemperature.getSelectedItem().toString() + "'";
//                              adding extra options to laundering and return the sum of the time duration of each choice
                            if (extra1.isChecked()) {
                                text += " ΣΤΥΨΙΜΟ";
                                tot += 15;
                            }
                            if (extra2.isChecked()) {
                                text += " ΞΕΒΓΑΛΜΑ";
                                tot += 20;
                            }
//                            only scheduling stuff should be visible
                            datePicker.setVisibility(View.VISIBLE);
                            timePicker.setVisibility(View.VISIBLE);
                            startTxt.setVisibility(View.INVISIBLE);
                            scheduled_start.setVisibility(View.VISIBLE);
                            cancel_scheduled.setVisibility(View.VISIBLE);

//                            setting DatePicker pop-up
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
//                            The first available date is today
                            mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                            mDatePicker.setTitle("ΕΠΙΛΕΞΤΕ ΗΜΕΡΟΜΗΝΙΑ");
                            mDatePicker.show();

//                            setting TimePicker pop-up
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

//                            We had to hide the cancel button in order to make sure that the user will not be able to skip the TimePicker choice
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
//                        light program is selected
                        else if (lightb) {
                            String text = "ΕΛΑΦΡΥ ";
                            int tot = 60;
//                          Adding temperature degrees to the textview
                            text += spinnerTemperature.getSelectedItem().toString() + "'";
//                            adding extra options to laundering and return the sum of the time duration of each choice
                            if (extra1.isChecked()) {
                                text += " ΣΤΥΨΙΜΟ";
                                tot += 15;
                            }
                            if (extra2.isChecked()) {
                                text += " ΞΕΒΓΑΛΜΑ";
                                tot += 20;
                            }

//                            only scheduling stuff should be visible
                            datePicker.setVisibility(View.VISIBLE);
                            timePicker.setVisibility(View.VISIBLE);
                            startTxt.setVisibility(View.INVISIBLE);
                            scheduled_start.setVisibility(View.VISIBLE);
                            cancel_scheduled.setVisibility(View.VISIBLE);

//                            setting DatePicker pop-up
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
//                            The first available date is today
                            mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                            mDatePicker.setTitle("ΕΠΙΛΕΞΤΕ ΗΜΕΡΟΜΗΝΙΑ");
                            mDatePicker.show();

//                            setting TimePicker pop-up
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
//                                We had to hide the cancel button in order to make sure that the user will not be able to skip the TimePicker choice
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
//                            strong program is selected
                            String text = "ΙΣΧΥΡΟ ";
                            int tot = 60;
//                            Adding temperature degrees to the textview
                            text += spinnerTemperature.getSelectedItem().toString() + "'";
//                              adding extra options to laundering and return the sum of the time duration of each choice
                            if (extra1.isChecked()) {
                                text += " ΣΤΥΨΙΜΟ";
                                tot += 15;
                            }
                            if (extra2.isChecked()) {
                                text += " ΞΕΒΓΑΛΜΑ";
                                tot += 20;
                            }

//                            only scheduling stuff should be visible
                            datePicker.setVisibility(View.VISIBLE);
                            timePicker.setVisibility(View.VISIBLE);
                            startTxt.setVisibility(View.INVISIBLE);
                            scheduled_start.setVisibility(View.VISIBLE);
                            cancel_scheduled.setVisibility(View.VISIBLE);

//                            setting DatePicker pop-up
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
//                            The first available date is today
                            mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                            mDatePicker.setTitle("ΕΠΙΛΕΞΤΕ ΗΜΕΡΟΜΗΝΙΑ");
                            mDatePicker.show();

//                            setting TimePicker pop-up
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

//                            We had to hide the cancel button in order to make sure that the user will not be able to skip the TimePicker choice
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

//        Button for cancelling the scheduling laundering, visible only in scheduling mode
        cancel_scheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.cancel_scheduled);
                    music.start();
                }
                Snackbar.make(view, "Η ΠΡΟΓΡΑΜΜΑΤΙΣΜΕΝΗ ΠΛΥΣΗ ΑΚΥΡΩΘΗΚΕ", Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show();
//                We do not change intent because after scheduling confirmation, a message is visible to the user
                Intent intent = new Intent(FirstFragment.this, FirstFragment.class);
                startActivity(intent);
            }
        });

//        Scheduled start button, visible only in scheduling mode
        scheduled_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceOn) {
                    MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.successfully_scheduling);
                    music.start();
                }
                Snackbar.make(view, "Η ΠΛΥΣΗ ΣΑΣ ΠΡΟΓΡΑΜΜΑΤΙΣΤΗΚΕ ΜΕ ΕΠΙΤΥΧΙΑ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                We do not change intent because after scheduling confirmation, a message is visible to the user
//                Intent intent = new Intent(FirstFragment.this, MainActivity.class);
//                intent.putExtra("VOICE_ON", voiceOn);
//                startActivity(intent);
            }
        });

//        Starting laundering
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  User has to choose a program for the laundering to start, otherwise a message warns them to choose one
                if (!normalb && !lightb && !strongb && !favorite.isChecked()) {
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.select_program_to_start);
                        music.start();
                    }
                    Snackbar.make(view, "ΠΡΕΠΕΙ ΝΑ ΕΠΙΛΕΞΕΤΕ ΠΡΟΓΡΑΜΜΑ ΓΙΑ ΝΑ ΞΕΚΙΝΗΣΕΤΕ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else if (pressedSchedule){
//                    button for scheduling laundering is pressed
                    if (voiceOn) {
                        MediaPlayer music = MediaPlayer.create(FirstFragment.this, R.raw.successfully_scheduling);
                        music.start();
                    }
                    Snackbar.make(view, "Η ΠΛΥΣΗ ΣΑΣ ΠΡΟΓΡΑΜΜΑΤΙΣΤΗΚΕ ΜΕ ΕΠΙΤΥΧΙΑ", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }
                else if (favorite.isChecked()) {
//                    favourite program is selected
                    String text = "ΚΑΝΟΝΙΚΟ 15' ΘΕΡΜΟΚΡΑΣΙΑ 30 ΞΕΒΓΑΛΜΑ ΣΤΥΨΙΜΟ";
                    int tot = 50;
                    Intent intent = new Intent(FirstFragment.this, Washing.class);
//                    Sending information to the next fragment
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
//                        normal program is selected
                        String text = "ΚΑΝΟΝΙΚΟ ";
                        int tot = 15;
//                        Adding temperature degrees to the textview
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
//                          adding extra options to laundering and return the sum of the time duration of each choice
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
//                        Sending information to the next fragment
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
//                        light program is selected
                        String text = "ΕΛΑΦΡΥ ";
                        int tot = 60;
//                        Adding temperature degrees to the textview
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
//                          adding extra options to laundering and return the sum of the time duration of each choice
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
//                        Sending information to the next fragment
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
//                        strong program is selected
                        String text = "ΙΣΧΥΡΟ ";
                        int tot = 60;
//                        Adding temperature degrees to the textview
                        text += spinnerTemperature.getSelectedItem().toString() + "'";
//                          adding extra options to laundering and return the sum of the time duration of each choice
                        if (extra1.isChecked()) {
                            text += " ΣΤΥΨΙΜΟ";
                            tot += 15;
                        }
                        if (extra2.isChecked()) {
                            text += " ΞΕΒΓΑΛΜΑ";
                            tot += 20;
                        }
                        Intent intent = new Intent(FirstFragment.this, Washing.class);
//                        Sending information to the next fragment
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

//   Function where the appropriate sound is sounded based on what program is selected
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


//  We cannot use the back button that Android offers
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}




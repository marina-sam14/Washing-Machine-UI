package com.example.washingmachine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.washingmachine.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends AppCompatActivity {

//    private AlertDialog.Builder dialogBuilder;
//    private AlertDialog dialog;
//    TextView helper_choices;
//    ImageButton helper = (ImageButton)findViewById(R.id.helper);



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);


        Spinner spinnerTemperature = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTemperature.setAdapter(adapter);

        CheckBox progrBox1 = (CheckBox) findViewById(R.id.normalcheckBox6);
        CheckBox progrBox2 = (CheckBox) findViewById(R.id.normalcheckBox7);
        CheckBox progrBox3 = (CheckBox) findViewById(R.id.normalcheckBox5);
        CheckBox favourite = (CheckBox) findViewById(R.id.normalcheckBox3);

        TextView normal = (TextView) findViewById(R.id.normal4);
        TextView light = (TextView) findViewById(R.id.normal2);
        TextView powerful = (TextView) findViewById(R.id.normal3);

        CheckBox extra1 = (CheckBox) findViewById(R.id.normalcheckBox8);
        CheckBox extra2 = (CheckBox) findViewById(R.id.normalcheckBox9);

        ImageButton start = (ImageButton) findViewById(R.id.start);

        ImageButton helper = (ImageButton) findViewById(R.id.helper);



        progrBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    progrBox2.setEnabled(false);
                    progrBox3.setEnabled(false);
                    favourite.setEnabled(false);




//                    Log.i("mytag","ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ");
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ ΔΙΑΡΚΕΙΑΣ 15' ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    progrBox2.setEnabled(true);
                    progrBox3.setEnabled(true);
                    favourite.setEnabled(true);

//                    Log.i("mytag","ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ");

                }

            }
        });

        progrBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    progrBox1.setEnabled(false);
                    progrBox3.setEnabled(false);
                    favourite.setEnabled(false);
//                    Log.i("mytag","ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ");
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΕΛΑΦΡΥ ΠΡΟΓΡΑΜΜΑ ΔΙΑΡΚΕΙΑΣ 60' ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    progrBox1.setEnabled(true);
                    progrBox3.setEnabled(true);
                    favourite.setEnabled(true);
                }

            }
        });

        progrBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    progrBox1.setEnabled(false);
                    progrBox2.setEnabled(false);
                    favourite.setEnabled(false);
//                    Log.i("mytag","ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ");
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΙΣΧΥΡΟ ΠΡΟΓΡΑΜΜΑ ΔΙΑΡΚΕΙΑΣ 60' ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    progrBox1.setEnabled(true);
                    progrBox2.setEnabled(true);
                    favourite.setEnabled(true);
                }

            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
//                    Log.i("mytag","ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ");
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΤΟ ΑΓΑΠΗΜΕΝΟ ΣΑΣ ΠΡΟΓΡΑΜΜΑ ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });


        extra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΣΤΥΨΙΜΟ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        extra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΞΕΒΓΑΛΜΑ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstFragment.this, Helper.class);
                startActivity(intent);
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!progrBox1.isChecked() && !progrBox2.isChecked() && !progrBox3.isChecked() && !favourite.isChecked()){
                    Snackbar.make(view, "ΠΡΕΠΕΙ ΝΑ ΕΠΙΛΕΞΕΤΕ ΠΡΟΓΡΑΜΜΑ ΓΙΑ ΝΑ ΞΕΚΙΝΗΣΕΤΕ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (favourite.isChecked()){
                    String text = "ΚΑΝΟΝΙΚΟ 15' ΘΕΡΜΟΚΡΑΣΙΑ 30 ΞΕΒΓΑΛΜΑ ΣΤΥΨΙΜΟ";
                    int tot = 50;
                    Intent intent = new Intent(FirstFragment.this, Laundring.class);
                    intent.putExtra("WASH_TEXT", text);
                    intent.putExtra("TOTAL_TIME", tot);
                    startActivity(intent);
                } else {
                    if (progrBox1.isChecked()){
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
                        Intent intent = new Intent(FirstFragment.this, Laundring.class);
                        intent.putExtra("WASH_TEXT", text);
                        intent.putExtra("TOTAL_TIME", tot);
                        startActivity(intent);
                    } else if (progrBox2.isChecked()){
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
                        Intent intent = new Intent(FirstFragment.this, Laundring.class);
                        intent.putExtra("WASH_TEXT", text);
                        intent.putExtra("TOTAL_TIME", tot);
                        startActivity(intent);
                    } else if (progrBox3.isChecked()) {
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
                        Intent intent = new Intent(FirstFragment.this, Laundring.class);
                        intent.putExtra("WASH_TEXT", text);
                        intent.putExtra("TOTAL_TIME", tot);
                        startActivity(intent);
                    }


                }
            }

        });



    }
//    public void createHelper() {
//        dialogBuilder = new AlertDialog.Builder(this);
//        final View popUp = getLayoutInflater().inflate(R.layout.helper,null);
//
//    }

}




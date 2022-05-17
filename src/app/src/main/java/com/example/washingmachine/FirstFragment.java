package com.example.washingmachine;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.washingmachine.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends AppCompatActivity {

//    private FragmentFirstBinding binding;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);


        Spinner spinnerLanguages = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        CheckBox progrBox1 = (CheckBox) findViewById(R.id.normalcheckBox6);
        CheckBox progrBox2 = (CheckBox) findViewById(R.id.normalcheckBox7);
        CheckBox progrBox3 = (CheckBox) findViewById(R.id.normalcheckBox5);
        CheckBox favourite = (CheckBox) findViewById(R.id.normalcheckBox3);


        progrBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    progrBox2.setEnabled(false);
                    progrBox3.setEnabled(false);
//                    Log.i("mytag","ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ");
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ ΔΙΑΡΚΕΙΑΣ 15' ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if (!checked){
                    progrBox2.setEnabled(true);
                    progrBox3.setEnabled(true);
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
//                    Log.i("mytag","ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ");
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΕΛΑΦΡΥ ΠΡΟΓΡΑΜΜΑ ΔΙΑΡΚΕΙΑΣ 60' ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        progrBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
//                    Log.i("mytag","ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΚΑΝΟΝΙΚΟ ΠΡΟΓΡΑΜΜΑ");
                    Snackbar.make(v, "ΕΧΕΤΕ ΕΠΙΛΕΞΕΙ ΙΣΧΥΡΟ ΠΡΟΓΡΑΜΜΑ ΔΙΑΡΚΕΙΑΣ 60' ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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

        CheckBox extra1 = (CheckBox) findViewById(R.id.normalcheckBox8);
        CheckBox extra2 = (CheckBox) findViewById(R.id.normalcheckBox9);
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

        ImageButton start = (ImageButton) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!progrBox1.isChecked() && !progrBox2.isChecked() && !progrBox3.isChecked() && !favourite.isChecked()){
                    Snackbar.make(view, "ΠΡΕΠΕΙ ΝΑ ΕΠΙΛΕΞΕΤΕ ΠΡΟΓΡΑΜΜΑ ΓΙΑ ΝΑ ΞΕΚΙΝΗΣΕΤΕ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (favourite.isChecked()){
                    Intent intent = new Intent(FirstFragment.this, Laundring.class);
                    startActivity(intent);
                }
                else {
//                    an kanoume diaforetika gia kathe thermokrasia, edw if-else
                    Intent intent = new Intent(FirstFragment.this, Laundring.class);
                    startActivity(intent);
                }
            }
        });


    }


}




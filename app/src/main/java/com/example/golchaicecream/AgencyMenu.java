package com.example.golchaicecream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicInteger;

public class AgencyMenu extends AppCompatActivity {



    EditText[] editTexts = new EditText[19];
    Button rev;

    TextView tv;
    EditText etNotes;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_menu);

        generate();
        callOnClickListeners();


    }

    private void callOnClickListeners() {

        rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDetailsOfObject();


            }
        });

    }

    private void setDetailsOfObject() {


        Intent intent = new Intent(getApplicationContext(),AgencyPDF.class);
        addEditTextValues(editTexts);


        intent.putExtra("kacchaQty",String.valueOf(editTexts[0].getText()));
        intent.putExtra("litchiQty",String.valueOf(editTexts[1].getText()));
        intent.putExtra("strawQty",String.valueOf(editTexts[2].getText()));
        intent.putExtra("colaQty",String.valueOf(editTexts[3].getText()));
        intent.putExtra("pineQty",String.valueOf(editTexts[4].getText()));
        intent.putExtra("orangeQty",String.valueOf(editTexts[5].getText()));
        intent.putExtra("mangoQty",String.valueOf(editTexts[6].getText()));
        intent.putExtra("cupSQty",String.valueOf(editTexts[7].getText()));
        intent.putExtra("cupBQty",String.valueOf(editTexts[8].getText()));
        intent.putExtra("chocoSQty",String.valueOf(editTexts[9].getText()));
        intent.putExtra("chocoBQty",String.valueOf(editTexts[10].getText()));
        intent.putExtra("matkaQty",String.valueOf(editTexts[11].getText()));
        intent.putExtra("coneSQty",String.valueOf(editTexts[12].getText()));
        intent.putExtra("coneBQty",String.valueOf(editTexts[13].getText()));
        intent.putExtra("keshaerQty",String.valueOf(editTexts[15].getText()));
        intent.putExtra("bonanzaQty",String.valueOf(editTexts[16].getText()));
        intent.putExtra("familyQty",String.valueOf(editTexts[17].getText()));
        intent.putExtra("family2Qty",String.valueOf(editTexts[18].getText()));
        intent.putExtra("nuttyQty",String.valueOf(editTexts[14].getText()));
        intent.putExtra("Notes",etNotes.getText().toString());

        startActivity(intent);
    }


    private void generate() {
        editTexts[0] = findViewById(R.id.tvKaccha);
        editTexts[1]= findViewById(R.id.tvLitchi);
        editTexts[2] = findViewById(R.id.tvStraw);
        editTexts[3]= findViewById(R.id.tvCola);
        editTexts[4] = findViewById(R.id.tvPine);
        editTexts[5] = findViewById(R.id.tvOrang);
        editTexts[6]= findViewById(R.id.tvMango);
        editTexts[7]= findViewById(R.id.tvCups);
        editTexts[8] = findViewById(R.id.tvCupb);
        editTexts[9]= findViewById(R.id.tvChocos);
        editTexts[10]= findViewById(R.id.choco);
        editTexts[11]= findViewById(R.id.tvMatka);
        editTexts[12]= findViewById(R.id.tvCones);
        editTexts[13]= findViewById(R.id.tvConeb);
        editTexts[14]= findViewById(R.id.tvNutty);
        editTexts[15]= findViewById(R.id.tvPista);
        editTexts[16]= findViewById(R.id.tvBonanza);
        editTexts[17]= findViewById(R.id.tvFamily);
        editTexts[18]= findViewById(R.id.tvFamily2);

        etNotes=findViewById(R.id.mynotes);

        rev = findViewById(R.id.btnOrder);

        tv = findViewById(R.id.second_edit_text);

    }

    public void addEditTextValues(EditText[] editTexts) {

        for (int i = 0; i < editTexts.length; i++) {

            String text = editTexts[i].getText().toString().trim();

            if (text.isEmpty()) {
                editTexts[i].setText("0");

            }
        }
    }




}

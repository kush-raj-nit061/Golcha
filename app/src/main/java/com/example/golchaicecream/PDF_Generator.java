package com.example.golchaicecream;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDF_Generator extends AppCompatActivity {

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("record/"+fAuth.getCurrentUser().getUid());

    Button btnGenerate,btnHome;
    long invoiceNum;
    TextView tvTotal,tvCommision,tvDues,tv,tvT;
    EditText[] editTexts = new EditText[18];
    TextView[] textViews = new TextView[18];
    DetailsObj detailsObj = new DetailsObj();



    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        generate();
        Toast.makeText(getApplicationContext(), "This is my Toast message!",
                Toast.LENGTH_LONG).show();
        callOnClickListeners();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoiceNum = dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void callOnClickListeners() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                detailsObj.invoiceNo=invoiceNum+ 1;
                detailsObj.date = new Date().getTime();
                detailsObj.kacchaQty = Integer.parseInt(String.valueOf(editTexts[0].getText()));


                myRef.child(String.valueOf(invoiceNum+1)).setValue(detailsObj);


            }
        });



    }

    private void generate() {
        btnGenerate = findViewById(R.id.btnBill);
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
        editTexts[14]= findViewById(R.id.tvPista);
        editTexts[15]= findViewById(R.id.tvBonanza);
        editTexts[16]= findViewById(R.id.tvFamily);
        editTexts[17]= findViewById(R.id.tvNutty);

        textViews[0] = findViewById(R.id.tvTotal1);
        textViews[1]= findViewById(R.id.tvTotal2);
        textViews[2] = findViewById(R.id.tvTotal3);
        textViews[3]= findViewById(R.id.tvTotal4);
        textViews[4] = findViewById(R.id.tvTotal5);
        textViews[5] = findViewById(R.id.tvTotal6);
        textViews[6]= findViewById(R.id.tvTotal7);
        textViews[7]= findViewById(R.id.tvTotal8);
        textViews[8] = findViewById(R.id.tvTotal9);
        textViews[9]= findViewById(R.id.tvTotal10);
        textViews[10]= findViewById(R.id.tvTotal11);
        textViews[11]= findViewById(R.id.tvTotal12);
        textViews[12]= findViewById(R.id.tvTotal13);
        textViews[13]= findViewById(R.id.tvTotal14);
        textViews[14]= findViewById(R.id.tvTotal15);
        textViews[15]= findViewById(R.id.tvTotal16);
        textViews[16]= findViewById(R.id.tvTotal17);
        textViews[17]= findViewById(R.id.tvTotal18);

        tvT =findViewById(R.id.Tot);
        tvTotal = findViewById(R.id.tvTotal);
        tvCommision = findViewById(R.id.tvCommision);
        tv = findViewById(R.id.second_edit_text);
        tvDues = findViewById(R.id.tvDues);
        btnHome = findViewById(R.id.btnHome);

    }
}

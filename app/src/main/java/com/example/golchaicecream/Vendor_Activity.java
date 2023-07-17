package com.example.golchaicecream;
import androidx.appcompat.app.AppCompatActivity;
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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Vendor_Activity extends AppCompatActivity {


    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("record/Vendor/"+fAuth.getCurrentUser().getUid());

    EditText[] editTexts = new EditText[19];
    EditText[] editTextss = new EditText[19];

    TextView[] textViews = new TextView[19];
    EditText Comm,vendorDetails;
    Button Calc,btnGenerate;
    long invoiceNum;
    TextView tvTotal,tvCommision,tvDues,tv,tvT;
    int arr[] = {5,5,5,5,5,5,5,5,10,10,15,25,12,25,20,25,100,20,120};
    float total;
    DetailsObjVendor detailsObj = new DetailsObjVendor();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        generate();
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

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDetailsOfObject();


                myRef.child(String.valueOf(invoiceNum+1)).setValue(detailsObj);

                Intent intent = new Intent(Vendor_Activity.this,PDF_Activity.class);


            }
        });


        Calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float result = addEditTextValues(editTexts,editTextss,textViews);
                String commision = "0";
                String texts = Comm.getText().toString().trim();
                if (!texts.isEmpty()) {
                    commision=texts;
                }
                float comm = (Float.parseFloat(commision) * result) / 100;
                float dues = result-comm;
                tvTotal.setText(String.valueOf(result));
                tvCommision.setText(String.valueOf(comm));
                tvDues.setText(String.valueOf(dues));
            }
        });
    }

    private void setDetailsOfObject() {

        detailsObj.invoiceNo=invoiceNum+ 1;
        detailsObj.date = new Date().getTime();
        detailsObj.custName = vendorDetails.getText().toString();
        detailsObj.kacchaQtyDepart = Integer.parseInt(String.valueOf(editTexts[0].getText()));
        detailsObj.litchiQtyDepart = Integer.parseInt(String.valueOf(editTexts[1].getText()));
        detailsObj.strawQtyDepart = Integer.parseInt(String.valueOf(editTexts[2].getText()));
        detailsObj.colaQtyDepart = Integer.parseInt(String.valueOf(editTexts[3].getText()));
        detailsObj.pineQtyDepart = Integer.parseInt(String.valueOf(editTexts[4].getText()));
        detailsObj.orangeQtyDepart = Integer.parseInt(String.valueOf(editTexts[5].getText()));
        detailsObj.mangoQtyDepart = Integer.parseInt(String.valueOf(editTexts[6].getText()));
        detailsObj.cupSQtyDepart = Integer.parseInt(String.valueOf(editTexts[7].getText()));
        detailsObj.cupBQtyDepart = Integer.parseInt(String.valueOf(editTexts[8].getText()));
        detailsObj.chocoSQtyDepart = Integer.parseInt(String.valueOf(editTexts[9].getText()));
        detailsObj.chocoBQtyDepart = Integer.parseInt(String.valueOf(editTexts[10].getText()));
        detailsObj.matkaQtyDepart = Integer.parseInt(String.valueOf(editTexts[11].getText()));
        detailsObj.coneSQtyDepart = Integer.parseInt(String.valueOf(editTexts[12].getText()));
        detailsObj.coneBQtyDepart = Integer.parseInt(String.valueOf(editTexts[13].getText()));
        detailsObj.keshaerQtyDepart = Integer.parseInt(String.valueOf(editTexts[14].getText()));
        detailsObj.bonanzaQtyDepart = Integer.parseInt(String.valueOf(editTexts[15].getText()));
        detailsObj.familyQtyDepart= Integer.parseInt(String.valueOf(editTexts[16].getText()));
        detailsObj.family2QtyDepart= Integer.parseInt(String.valueOf(editTexts[18].getText()));
        detailsObj.nuttyQtyDepart = Integer.parseInt(String.valueOf(editTexts[17].getText()));


        detailsObj.kacchaQtyReturn = Integer.parseInt(String.valueOf(editTextss[0].getText()));
        detailsObj.litchiQtyReturn = Integer.parseInt(String.valueOf(editTextss[1].getText()));
        detailsObj.strawQtyReturn = Integer.parseInt(String.valueOf(editTextss[2].getText()));
        detailsObj.colaQtyReturn = Integer.parseInt(String.valueOf(editTextss[3].getText()));
        detailsObj.pineQtyReturn = Integer.parseInt(String.valueOf(editTextss[4].getText()));
        detailsObj.orangeQtyReturn = Integer.parseInt(String.valueOf(editTextss[5].getText()));
        detailsObj.mangoQtyReturn = Integer.parseInt(String.valueOf(editTextss[6].getText()));
        detailsObj.cupSQtyReturn = Integer.parseInt(String.valueOf(editTextss[7].getText()));
        detailsObj.cupBQtyReturn = Integer.parseInt(String.valueOf(editTextss[8].getText()));
        detailsObj.chocoSQtyReturn = Integer.parseInt(String.valueOf(editTextss[9].getText()));
        detailsObj.chocoBQtyReturn = Integer.parseInt(String.valueOf(editTextss[10].getText()));
        detailsObj.matkaQtyReturn = Integer.parseInt(String.valueOf(editTextss[11].getText()));
        detailsObj.coneSQtyReturn = Integer.parseInt(String.valueOf(editTextss[12].getText()));
        detailsObj.coneBQtyReturn = Integer.parseInt(String.valueOf(editTextss[13].getText()));
        detailsObj.keshaerQtyReturn = Integer.parseInt(String.valueOf(editTextss[14].getText()));
        detailsObj.bonanzaQtyReturn = Integer.parseInt(String.valueOf(editTextss[15].getText()));
        detailsObj.familyQtyReturn= Integer.parseInt(String.valueOf(editTextss[16].getText()));
        detailsObj.family2QtyReturn= Integer.parseInt(String.valueOf(editTextss[18].getText()));
        detailsObj.nuttyQtyReturn = Integer.parseInt(String.valueOf(editTextss[17].getText()));


        detailsObj.kacchaPrice = Integer.parseInt(String.valueOf(textViews[0].getText()));
        detailsObj.litchiPrice = Integer.parseInt(String.valueOf(textViews[1].getText()));
        detailsObj.strawPrice = Integer.parseInt(String.valueOf(textViews[2].getText()));
        detailsObj.colaPrice = Integer.parseInt(String.valueOf(textViews[3].getText()));
        detailsObj.pinePrice = Integer.parseInt(String.valueOf(textViews[4].getText()));
        detailsObj.orangePrice = Integer.parseInt(String.valueOf(textViews[5].getText()));
        detailsObj.mangoPrice = Integer.parseInt(String.valueOf(textViews[6].getText()));
        detailsObj.cupSPrice = Integer.parseInt(String.valueOf(textViews[7].getText()));
        detailsObj.cupBPrice = Integer.parseInt(String.valueOf(textViews[8].getText()));
        detailsObj.chocoSPrice = Integer.parseInt(String.valueOf(textViews[9].getText()));
        detailsObj.chocoBPrice = Integer.parseInt(String.valueOf(textViews[10].getText()));
        detailsObj.matkaPrice = Integer.parseInt(String.valueOf(textViews[11].getText()));
        detailsObj.coneSPrice = Integer.parseInt(String.valueOf(textViews[12].getText()));
        detailsObj.coneBPrice = Integer.parseInt(String.valueOf(textViews[13].getText()));
        detailsObj.keshaerPrice = Integer.parseInt(String.valueOf(textViews[14].getText()));
        detailsObj.bonanzaPrice = Integer.parseInt(String.valueOf(textViews[15].getText()));
        detailsObj.familyPrice = Integer.parseInt(String.valueOf(textViews[16].getText()));
        detailsObj.family2Price = Integer.parseInt(String.valueOf(textViews[18].getText()));
        detailsObj.nuttyPrice = Integer.parseInt(String.valueOf(textViews[17].getText()));


        detailsObj.total = Double.parseDouble(String.valueOf(tvTotal.getText()));
        detailsObj.dues = Double.parseDouble(String.valueOf(tvDues.getText()));
        detailsObj.commision = Double.parseDouble(String.valueOf(tvCommision.getText()));
        detailsObj.commision_percentage = Integer.parseInt(String.valueOf(Comm.getText()));


    }

    private void generate() {
        vendorDetails=findViewById(R.id.vendorDetails);
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
        editTexts[10]= findViewById(R.id.tvChocob);
        editTexts[11]= findViewById(R.id.tvMatka);
        editTexts[12]= findViewById(R.id.tvCones);
        editTexts[13]= findViewById(R.id.tvConeb);
        editTexts[14]= findViewById(R.id.tvPista);
        editTexts[15]= findViewById(R.id.tvBonanza);
        editTexts[16]= findViewById(R.id.tvFamily);
        editTexts[18]= findViewById(R.id.tvFamily2);
        editTexts[17]= findViewById(R.id.tvNutty);

        editTextss[0] = findViewById(R.id.tvKacchas);
        editTextss[1]= findViewById(R.id.tvLitchis);
        editTextss[2] = findViewById(R.id.tvStraws);
        editTextss[3]= findViewById(R.id.tvColas);
        editTextss[4] = findViewById(R.id.tvPines);
        editTextss[5] = findViewById(R.id.tvOrangs);
        editTextss[6]= findViewById(R.id.tvMangos);
        editTextss[7]= findViewById(R.id.tvCupss);
        editTextss[8] = findViewById(R.id.tvCupbs);
        editTextss[9]= findViewById(R.id.tvChocoss);
        editTextss[10]= findViewById(R.id.tvChocobs);
        editTextss[11]= findViewById(R.id.tvMatkas);
        editTextss[12]= findViewById(R.id.tvConess);
        editTextss[13]= findViewById(R.id.tvConebs);
        editTextss[14]= findViewById(R.id.tvPistas);
        editTextss[15]= findViewById(R.id.tvBonanzas);
        editTextss[16]= findViewById(R.id.tvFamilys);
        editTextss[18]= findViewById(R.id.tvFamily2s);
        editTextss[17]= findViewById(R.id.tvNuttys);


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
        textViews[18]= findViewById(R.id.tvTotal19);
        tvT =findViewById(R.id.Tot);
        tvTotal = findViewById(R.id.tvTotal);
        Comm = findViewById(R.id.etComm);
        tvCommision = findViewById(R.id.tvCommision);
        tv = findViewById(R.id.second_edit_text);
        Calc = findViewById(R.id.btnCT);
        tvDues = findViewById(R.id.tvDues);
    }

    public int addEditTextValues(EditText[] editTexts,EditText[] editTextss,TextView[] textView) {
        int sum = 0;
        tvT.setText("Total");
        for (int i = 0; i < editTexts.length; i++) {
            String text = editTexts[i].getText().toString().trim();
            String texts = editTextss[i].getText().toString().trim();

            if (!text.isEmpty()) {
                try {
                    int value = Integer.parseInt(text) - Integer.parseInt(texts);
                    value *= arr[i];
                    textViews[i].setText(String.valueOf(value));
                    sum += value;
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            }
        }
        return sum;
    }
}
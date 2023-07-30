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

public class Menu_Activity extends AppCompatActivity {

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("record/Agency/"+fAuth.getCurrentUser().getUid());
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DatabaseReference retriveRef;


    String userID=fAuth.getCurrentUser().getUid();
    String user;

    Button btnGenerate,btnHome;
//    long invoiceNum;

    EditText[] editTexts = new EditText[19];
    Button Calc;
    String invoice,invoices,ids;

    TextView[] textViews = new TextView[19];
    TextView tvTotal,tvCommision,tvDues,tv,tvT;
    EditText etNotes;
//    DetailsObj detailsObj = new DetailsObj();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    int arr[] = {250,250,250,250,250,250,250,150,240,300,360,300,420,500,240,200,100,400,120};
    float total;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent i = getIntent();
        invoice = i.getStringExtra("inv");




        retriveRef = FirebaseDatabase.getInstance().getReference().child("record/AgencyLatest").child(invoice);

        generate();
        callOnClickListeners();

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                invoiceNum = dataSnapshot.getChildrenCount();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });


    }

    private void callOnClickListeners() {

        retriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invoices = String.valueOf((long) snapshot.child("invoiceNo").getValue());
                ids = String.valueOf(snapshot.child("userId").getValue());

                editTexts[0].setText(String.valueOf(snapshot.child("kacchaQty").getValue()));
                editTexts[1].setText(String.valueOf(snapshot.child("litchiQty").getValue()));
                editTexts[2].setText(String.valueOf(snapshot.child("strawQty").getValue()));
                editTexts[3].setText(String.valueOf(snapshot.child("colaQty").getValue()));
                editTexts[4].setText(String.valueOf(snapshot.child("pineQty").getValue()));
                editTexts[5].setText(String.valueOf(snapshot.child("orangeQty").getValue()));
                editTexts[6].setText(String.valueOf(snapshot.child("mangoQty").getValue()));
                editTexts[7].setText(String.valueOf(snapshot.child("cupSQty").getValue()));
                editTexts[8].setText(String.valueOf(snapshot.child("cupBQty").getValue()));
                editTexts[9].setText(String.valueOf(snapshot.child("chocoSQty").getValue()));
                editTexts[10].setText(String.valueOf(snapshot.child("chocoBQty").getValue()));
                editTexts[11].setText(String.valueOf(snapshot.child("matkaQty").getValue()));
                editTexts[12].setText(String.valueOf(snapshot.child("coneSQty").getValue()));
                editTexts[13].setText(String.valueOf(snapshot.child("coneBQty").getValue()));
                editTexts[14].setText(String.valueOf(snapshot.child("keshaerQty").getValue()));
                editTexts[15].setText(String.valueOf(snapshot.child("bonanzaQty").getValue()));
                editTexts[16].setText(String.valueOf(snapshot.child("familyQty").getValue()));
                editTexts[17].setText(String.valueOf(snapshot.child("nuttyQty").getValue()));
                editTexts[18].setText(String.valueOf(snapshot.child("family2Qty").getValue()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float result = addEditTextValues(editTexts,textViews);
                fStore.collection("users").document(userID)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                if (document.exists()) {
                                    String com = document.getString("commission");
                                    int commission = 0;
                                    assert com != null;
                                    commission= Integer.parseInt(com);
                                    Toast.makeText(getApplicationContext(), "Commission = "+com+"%", Toast.LENGTH_SHORT).show();

                                    float comm = (commission * result) / 100;
                                    float dues = result-comm;
                                    tvTotal.setText(String.valueOf(result));
                                    tvCommision.setText(String.valueOf(comm));
                                    tvDues.setText(String.valueOf(dues));

                                }
                            } else {
                                // Handle the error
                                Exception exception = task.getException();
                                // Log or display an error message
                            }
                        });


            }
        });



        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float result = addEditTextValues(editTexts,textViews);
                fStore.collection("users").document(userID)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                if (document.exists()) {
                                    String com = document.getString("commission");
                                    int commission = 0;
                                    commission= Integer.parseInt(com);
                                    Toast.makeText(getApplicationContext(), "Commission = "+com+"%", Toast.LENGTH_SHORT).show();

                                    float comm = (commission * result) / 100;
                                    float dues = result-comm;
                                    tvTotal.setText(String.valueOf(result));
                                    tvCommision.setText(String.valueOf(comm));
                                    tvDues.setText(String.valueOf(dues));

                                    setDetailsOfObject();
//                                  myRef.child(String.valueOf(invoiceNum+1)).setValue(detailsObj);

                                }
                            } else {
                                // Handle the error
                                Exception exception = task.getException();
                                // Log or display an error message
                            }
                        });


//                Intent intent = new Intent(Menu_Activity.this,PDF_Activity.class);
//                startActivity(intent);
            }
        });



    }

    private void setDetailsOfObject() {
//        detailsObj.invoiceNo=invoiceNum+ 1;
//        detailsObj.date = new Date().getTime();
//        detailsObj.kacchaQty = Integer.parseInt(String.valueOf(editTexts[0].getText()));
//        detailsObj.litchiQty = Integer.parseInt(String.valueOf(editTexts[1].getText()));
//        detailsObj.strawQty = Integer.parseInt(String.valueOf(editTexts[2].getText()));
//        detailsObj.colaQty = Integer.parseInt(String.valueOf(editTexts[3].getText()));
//        detailsObj.pineQty = Integer.parseInt(String.valueOf(editTexts[4].getText()));
//        detailsObj.orangeQty = Integer.parseInt(String.valueOf(editTexts[5].getText()));
//        detailsObj.mangoQty = Integer.parseInt(String.valueOf(editTexts[6].getText()));
//        detailsObj.cupSQty = Integer.parseInt(String.valueOf(editTexts[7].getText()));
//        detailsObj.cupBQty = Integer.parseInt(String.valueOf(editTexts[8].getText()));
//        detailsObj.chocoSQty = Integer.parseInt(String.valueOf(editTexts[9].getText()));
//        detailsObj.chocoBQty = Integer.parseInt(String.valueOf(editTexts[10].getText()));
//        detailsObj.matkaQty = Integer.parseInt(String.valueOf(editTexts[11].getText()));
//        detailsObj.coneSQty = Integer.parseInt(String.valueOf(editTexts[12].getText()));
//        detailsObj.coneBQty = Integer.parseInt(String.valueOf(editTexts[13].getText()));
//        detailsObj.keshaerQty = Integer.parseInt(String.valueOf(editTexts[14].getText()));
//        detailsObj.bonanzaQty = Integer.parseInt(String.valueOf(editTexts[15].getText()));
//        detailsObj.familyQty= Integer.parseInt(String.valueOf(editTexts[16].getText()));
//        detailsObj.family2Qty= Integer.parseInt(String.valueOf(editTexts[18].getText()));
//        detailsObj.nuttyQty = Integer.parseInt(String.valueOf(editTexts[17].getText()));
//
//        detailsObj.kacchaPrice = Integer.parseInt(String.valueOf(textViews[0].getText()));
//        detailsObj.litchiPrice = Integer.parseInt(String.valueOf(textViews[1].getText()));
//        detailsObj.strawPrice = Integer.parseInt(String.valueOf(textViews[2].getText()));
//        detailsObj.colaPrice = Integer.parseInt(String.valueOf(textViews[3].getText()));
//        detailsObj.pinePrice = Integer.parseInt(String.valueOf(textViews[4].getText()));
//        detailsObj.orangePrice = Integer.parseInt(String.valueOf(textViews[5].getText()));
//        detailsObj.mangoPrice = Integer.parseInt(String.valueOf(textViews[6].getText()));
//        detailsObj.cupSPrice = Integer.parseInt(String.valueOf(textViews[7].getText()));
//        detailsObj.cupBPrice = Integer.parseInt(String.valueOf(textViews[8].getText()));
//        detailsObj.chocoSPrice = Integer.parseInt(String.valueOf(textViews[9].getText()));
//        detailsObj.chocoBPrice = Integer.parseInt(String.valueOf(textViews[10].getText()));
//        detailsObj.matkaPrice = Integer.parseInt(String.valueOf(textViews[11].getText()));
//        detailsObj.coneSPrice = Integer.parseInt(String.valueOf(textViews[12].getText()));
//        detailsObj.coneBPrice = Integer.parseInt(String.valueOf(textViews[13].getText()));
//        detailsObj.keshaerPrice = Integer.parseInt(String.valueOf(textViews[14].getText()));
//        detailsObj.bonanzaPrice = Integer.parseInt(String.valueOf(textViews[15].getText()));
//        detailsObj.familyPrice = Integer.parseInt(String.valueOf(textViews[16].getText()));
//        detailsObj.family2Price = Integer.parseInt(String.valueOf(textViews[18].getText()));
//        detailsObj.nuttyPrice = Integer.parseInt(String.valueOf(textViews[17].getText()));
//
//
//        detailsObj.total = Double.parseDouble(String.valueOf(tvTotal.getText()));
//        detailsObj.dues = Double.parseDouble(String.valueOf(tvDues.getText()));
//        detailsObj.commision = Double.parseDouble(String.valueOf(tvCommision.getText()));

        Intent intent = new Intent(getApplicationContext(),PDF_Activity.class);
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
        intent.putExtra("keshaerQty",String.valueOf(editTexts[14].getText()));
        intent.putExtra("bonanzaQty",String.valueOf(editTexts[15].getText()));
        intent.putExtra("familyQty",String.valueOf(editTexts[16].getText()));
        intent.putExtra("family2Qty",String.valueOf(editTexts[17].getText()));
        intent.putExtra("nuttyQty",String.valueOf(editTexts[18].getText()));
        intent.putExtra("Notes",etNotes.getText().toString());

        intent.putExtra("kacchaPrice",String.valueOf(textViews[0].getText()));
        intent.putExtra("litchiPrice",String.valueOf(textViews[1].getText()));
        intent.putExtra("strawPrice",String.valueOf(textViews[2].getText()));
        intent.putExtra("colaPrice",String.valueOf(textViews[3].getText()));
        intent.putExtra("pinePrice",String.valueOf(textViews[4].getText()));
        intent.putExtra("orangePrice",String.valueOf(textViews[5].getText()));
        intent.putExtra("mangoPrice",String.valueOf(textViews[6].getText()));
        intent.putExtra("cupSPrice",String.valueOf(textViews[7].getText()));
        intent.putExtra("cupBPrice",String.valueOf(textViews[8].getText()));
        intent.putExtra("chocoSPrice",String.valueOf(textViews[9].getText()));
        intent.putExtra("chocoBPrice",String.valueOf(textViews[10].getText()));
        intent.putExtra("matkaPrice",String.valueOf(textViews[11].getText()));
        intent.putExtra("coneSPrice",String.valueOf(textViews[12].getText()));
        intent.putExtra("coneBPrice",String.valueOf(textViews[13].getText()));
        intent.putExtra("keshaerPrice",String.valueOf(textViews[14].getText()));
        intent.putExtra("bonanzaPrice",String.valueOf(textViews[15].getText()));
        intent.putExtra("familyPrice",String.valueOf(textViews[16].getText()));
        intent.putExtra("family2Price",String.valueOf(textViews[17].getText()));
        intent.putExtra("nuttyPrice",String.valueOf(textViews[18].getText()));

        intent.putExtra("Total",String.valueOf(tvTotal.getText()));
        intent.putExtra("Commission",String.valueOf(tvCommision.getText()));
        intent.putExtra("Dues",String.valueOf(tvDues.getText()));
        intent.putExtra("inv",invoices);
        intent.putExtra("userids",ids);

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
        editTexts[14]= findViewById(R.id.tvPista);
        editTexts[15]= findViewById(R.id.tvBonanza);
        editTexts[16]= findViewById(R.id.tvFamily);
        editTexts[18]= findViewById(R.id.tvFamily2);
        editTexts[17]= findViewById(R.id.tvNutty);
        etNotes=findViewById(R.id.mynotes);


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
        textViews[18]= findViewById(R.id.tvTotal19);
        textViews[17]= findViewById(R.id.tvTotal18);

        tvT =findViewById(R.id.Tot);
        tvTotal = findViewById(R.id.tvTotal);
        tvCommision = findViewById(R.id.tvCommision);
        tv = findViewById(R.id.second_edit_text);
        Calc = findViewById(R.id.btnCT);
        tvDues = findViewById(R.id.tvDues);
        btnHome = findViewById(R.id.btnHome);
        btnGenerate = findViewById(R.id.btnBill);
    }

    public int addEditTextValues(EditText[] editTexts,TextView[] textView) {
        int sum = 0;
        tvT.setText("Total");
        for (int i = 0; i < editTexts.length; i++) {
            String text = editTexts[i].getText().toString().trim();

            if (!text.isEmpty()) {
                try {
                    int value = Integer.parseInt(text);
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

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
import android.graphics.drawable.AnimationDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.golchaicecream.Room.UserDao;
import com.example.golchaicecream.Room.UserDatabase;
import com.example.golchaicecream.Room.Users;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Vendor_Activity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText[] editTexts = new EditText[19];
    EditText[] editTextss = new EditText[19];
    TextView[] itemPrice=new TextView[19];
    FirebaseFirestore fsStore = FirebaseFirestore.getInstance();
    TextView[] textViews = new TextView[19];
    Integer[] s = new Integer[19];
    EditText Comm,vendorName,vendorAddress,vendorNotes;
    Button btnCTs,btnBills,btnUpdate;
    long invoiceNum;
    TextView tvTotal,tvCommision,tvDues,tv;
    int arr[] = new int[19];
    float total;
    private UserDatabase userDatabase;
    private UserDao userDao;
//    DetailsObjVendor detailsObj = new DetailsObjVendor();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

    @SuppressLint({"MissingInflatedId", "UseCompatLoadingForDrawables"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        Intent i = getIntent();
        Users users ;
        users =(Users) getIntent().getSerializableExtra("model");
        userDatabase = UserDatabase.getInstance(this);
        userDao = userDatabase.getDao();


        progressBar=findViewById(R.id.progressu);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout relativeLayout = findViewById(R.id.rele);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        try {
            animationDrawable.start();
        }catch (Exception e){
            relativeLayout.setBackground(getResources().getDrawable(R.drawable.background_2));
        }

        generate();

        vendorName.setText(String.valueOf(users.getName().toString()));
        vendorAddress.setText(String.valueOf(users.getAddress().toString()));
        vendorNotes.setText(String.valueOf(users.getNotes().toString()));
        editTexts[0].setText(String.valueOf(users.getKacchaQtyDepart()));
        editTexts[1].setText(String.valueOf(users.getLitchiQtyDepart()));
        editTexts[2].setText(String.valueOf(users.getStrawQtyDepart()));
        editTexts[3].setText(String.valueOf(users.getColaQtyDepart()));
        editTexts[4].setText(String.valueOf(users.getPineQtyDepart()));
        editTexts[5].setText(String.valueOf(users.getOrangeQtyDepart()));
        editTexts[6].setText(String.valueOf(users.getMangoQtyDepart()));
        editTexts[7].setText(String.valueOf(users.getCupSQtyDepart()));
        editTexts[8].setText(String.valueOf(users.getCupBQtyDepart()));
        editTexts[9].setText(String.valueOf(users.getChocoSQtyDepart()));
        editTexts[10].setText(String.valueOf(users.getChocoBQtyDepart()));
        editTexts[11].setText(String.valueOf(users.getMatkaQtyDepart()));
        editTexts[12].setText(String.valueOf(users.getConeSQtyDepart()));
        editTexts[13].setText(String.valueOf(users.getConeBQtyDepart()));
        editTexts[14].setText(String.valueOf(users.getNuttyQtyDepart()));
        editTexts[15].setText(String.valueOf(users.getKeshaerQtyDepart()));
        editTexts[16].setText(String.valueOf(users.getBonanzaQtyDepart()));
        editTexts[17].setText(String.valueOf(users.getFamilyQtyDepart()));
        editTexts[18].setText(String.valueOf(users.getFamily2QtyDepart()));
        Comm.setText(String.valueOf(users.getCommision()));







        callOnClickListeners(users);


    }

    private void callOnClickListeners(Users users) {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < editTexts.length; i++) {
                    String text = editTexts[i].getText().toString().trim();
                    if (text.isEmpty()) {
                        editTexts[i].setText("0");
                        text = "0";
                    }
                    s[i] = Integer.parseInt(editTexts[i].getText().toString().trim());
                }
                String si = datePatternFormat.format(new Date());
                String q = si.substring(0,11);

                Users userModel=new Users(users.getId(),q,Float.parseFloat(Comm.getText().toString()), vendorName.getText().toString(),vendorAddress.getText().toString(),"🛒",vendorNotes.getText().toString(),
                        s[0],s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],s[9],s[10],s[11],s[12],s[13],s[14],s[15],s[16],s[17],s[18]);
                userDao.update(userModel);
                Toast.makeText(getApplicationContext(),"Depart Details Updated Successfully",Toast.LENGTH_SHORT).show();
                finish();

            }
        });


        fsStore.collection("itemDetails").document("VendorPrices")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();

                        if (document.exists()) {

//                            Toast.makeText(getApplicationContext(), "Commission = "+com+"%", Toast.LENGTH_SHORT).show();
                            int a =1;

                            for (int i = 0;i<19;i++){
                                arr[i]= Integer.parseInt(document.getString(String.valueOf(a)));
                                itemPrice[i].setText(String.valueOf(arr[i]));
                                a++;

                            }
                            progressBar.setVisibility(View.GONE);


                        }
                    } else {
                        Exception exception = task.getException();
                    }
                });

        btnBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(vendorName.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter Name",
                                    Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(vendorAddress.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter Address",
                                    Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                for (int i = 0; i < editTexts.length; i++) {
                    String text = editTexts[i].getText().toString().trim();
                    if (text.isEmpty()) {
                        editTexts[i].setText("0");
                        text = "0";
                    }
                    s[i] = Integer.parseInt(editTexts[i].getText().toString().trim());
                }

                Users userModel=new Users(users.getId(),users.getDate(),Float.parseFloat(Comm.getText().toString()), vendorName.getText().toString(),vendorAddress.getText().toString(),"✔️",vendorNotes.getText().toString(),
                        s[0],s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],s[9],s[10],s[11],s[12],s[13],s[14],s[15],s[16],s[17],s[18]);
                userDao.update(userModel);
                Toast.makeText(getApplicationContext(),"Depart Details Updated Successfully",Toast.LENGTH_SHORT).show();
                finish();





                float result = addEditTextValues(editTexts,editTextss,textViews);
                if(result == 0){
                    Toast.makeText(getApplicationContext(),"Return Qty can't be greater than Depart qty",Toast.LENGTH_SHORT).show();
                    return;
                }
                String commision = "0";
                String texts = Comm.getText().toString().trim();

                if (!texts.isEmpty()) {
                    commision=texts;
                }
                Comm.setText(commision);
                float comm = (Float.parseFloat(commision) * result) / 100;
                float dues = result-comm;
                tvTotal.setText(String.valueOf(result));
                tvCommision.setText(String.valueOf(comm));
                tvDues.setText(String.valueOf(dues));



                setDetailsOfObject();


            }

        });


        btnCTs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float result = addEditTextValues(editTexts,editTextss,textViews);
                if(result == 0){
                    Toast.makeText(getApplicationContext(),"Return Qty can't be greater than Depart qty",Toast.LENGTH_SHORT).show();
                    return;
                }
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

    private void setValuesOfDepart(Users users) {

    }

    public int addEditTextValues(EditText[] editTexts,EditText[] editTextss,TextView[] textView) {
        int sum = 0;

        for (int i = 0; i < editTexts.length; i++) {
            String text = editTexts[i].getText().toString().trim();
            String texts = editTextss[i].getText().toString().trim();

            if(text.isEmpty()){
                editTexts[i].setText("0");
                text = "0";
            }
            if(texts.isEmpty()){
                editTextss[i].setText("0");
                texts = "0";
            }

            if (!text.isEmpty()) {
                try {
                    int value = Integer.parseInt(text) - Integer.parseInt(texts);
                    if(value<0){

                        return 0;
                    }else {
                        value *= arr[i];
                        textViews[i].setText(String.valueOf(value));
                        sum += value;
                    }

                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            }
        }
        return sum;
    }


    private void setDetailsOfObject() {

        Intent intent = new Intent(getApplicationContext(), VendorPDF.class);


        intent.putExtra("price1",String.valueOf(arr[0]));
        intent.putExtra("price2",String.valueOf(arr[1]));
        intent.putExtra("price3",String.valueOf(arr[2]));
        intent.putExtra("price4",String.valueOf(arr[3]));
        intent.putExtra("price5",String.valueOf(arr[4]));
        intent.putExtra("price6",String.valueOf(arr[5]));
        intent.putExtra("price7",String.valueOf(arr[6]));
        intent.putExtra("price8",String.valueOf(arr[7]));
        intent.putExtra("price9",String.valueOf(arr[8]));
        intent.putExtra("price10",String.valueOf(arr[9]));
        intent.putExtra("price11",String.valueOf(arr[10]));
        intent.putExtra("price12",String.valueOf(arr[11]));
        intent.putExtra("price13",String.valueOf(arr[12]));
        intent.putExtra("price14",String.valueOf(arr[13]));
        intent.putExtra("price15",String.valueOf(arr[14]));
        intent.putExtra("price16",String.valueOf(arr[15]));
        intent.putExtra("price17",String.valueOf(arr[16]));
        intent.putExtra("price18",String.valueOf(arr[17]));
        intent.putExtra("price19",String.valueOf(arr[18]));

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

        intent.putExtra("kacchaQtyR",String.valueOf(editTextss[0].getText()));
        intent.putExtra("litchiQtyR",String.valueOf(editTextss[1].getText()));
        intent.putExtra("strawQtyR",String.valueOf(editTextss[2].getText()));
        intent.putExtra("colaQtyR",String.valueOf(editTextss[3].getText()));
        intent.putExtra("pineQtyR",String.valueOf(editTextss[4].getText()));
        intent.putExtra("orangeQtyR",String.valueOf(editTextss[5].getText()));
        intent.putExtra("mangoQtyR",String.valueOf(editTextss[6].getText()));
        intent.putExtra("cupSQtyR",String.valueOf(editTextss[7].getText()));
        intent.putExtra("cupBQtyR",String.valueOf(editTextss[8].getText()));
        intent.putExtra("chocoSQtyR",String.valueOf(editTextss[9].getText()));
        intent.putExtra("chocoBQtyR",String.valueOf(editTextss[10].getText()));
        intent.putExtra("matkaQtyR",String.valueOf(editTextss[11].getText()));
        intent.putExtra("coneSQtyR",String.valueOf(editTextss[12].getText()));
        intent.putExtra("coneBQtyR",String.valueOf(editTextss[13].getText()));
        intent.putExtra("keshaerQtyR",String.valueOf(editTextss[15].getText()));
        intent.putExtra("bonanzaQtyR",String.valueOf(editTextss[16].getText()));
        intent.putExtra("familyQtyR",String.valueOf(editTextss[17].getText()));
        intent.putExtra("family2QtyR",String.valueOf(editTextss[18].getText()));
        intent.putExtra("nuttyQtyR",String.valueOf(editTextss[14].getText()));

        intent.putExtra("Notes",vendorNotes.getText().toString());

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
        intent.putExtra("keshaerPrice",String.valueOf(textViews[15].getText()));
        intent.putExtra("bonanzaPrice",String.valueOf(textViews[16].getText()));
        intent.putExtra("familyPrice",String.valueOf(textViews[17].getText()));
        intent.putExtra("family2Price",String.valueOf(textViews[18].getText()));
        intent.putExtra("nuttyPrice",String.valueOf(textViews[14].getText()));

        intent.putExtra("Total",String.valueOf(tvTotal.getText()));
        intent.putExtra("Commission",String.valueOf(tvCommision.getText()));
        intent.putExtra("Dues",String.valueOf(tvDues.getText()));


        intent.putExtra("custName",String.valueOf(vendorName.getText()));
        intent.putExtra("custAddress",String.valueOf(vendorAddress.getText()));



        startActivity(intent);
    }





    private void generate() {
        btnUpdate = findViewById(R.id.btnUpdate);

        itemPrice[0] = findViewById(R.id.itemPrice1);
        itemPrice[1] = findViewById(R.id.itemPrice2);
        itemPrice[2] = findViewById(R.id.itemPrice3);
        itemPrice[3] = findViewById(R.id.itemPrice4);
        itemPrice[4] = findViewById(R.id.itemPrice5);
        itemPrice[5] = findViewById(R.id.itemPrice6);
        itemPrice[6] = findViewById(R.id.itemPrice7);
        itemPrice[7] = findViewById(R.id.itemPrice8);
        itemPrice[8] = findViewById(R.id.itemPrice9);
        itemPrice[9] = findViewById(R.id.itemPrice10);
        itemPrice[10] = findViewById(R.id.itemPrice11);
        itemPrice[11] = findViewById(R.id.itemPrice12);
        itemPrice[12] = findViewById(R.id.itemPrice13);
        itemPrice[13] = findViewById(R.id.itemPrice14);
        itemPrice[14] = findViewById(R.id.itemPrice15);
        itemPrice[15] = findViewById(R.id.itemPrice16);
        itemPrice[16] = findViewById(R.id.itemPrice17);
        itemPrice[17] = findViewById(R.id.itemPrice18);
        itemPrice[18] = findViewById(R.id.itemPrice19);



        vendorName=findViewById(R.id.vendorName);
        vendorAddress = findViewById(R.id.vendorAddress);
        vendorNotes = findViewById(R.id.vendorDetails);
        editTexts[0] = findViewById(R.id.tvKacchaDep);
        editTexts[1]= findViewById(R.id.tvLitchiDep);
        editTexts[2] = findViewById(R.id.tvStrawDep);
        editTexts[3]= findViewById(R.id.tvColaDep);
        editTexts[4] = findViewById(R.id.tvPineDep);
        editTexts[5] = findViewById(R.id.tvOrangDep);
        editTexts[6]= findViewById(R.id.tvMangoDep);
        editTexts[7]= findViewById(R.id.tvCupsDep);
        editTexts[8] = findViewById(R.id.tvCupbDep);
        editTexts[9]= findViewById(R.id.tvChocosDep);
        editTexts[10]= findViewById(R.id.tvChocobDep);
        editTexts[11]= findViewById(R.id.tvMatkaDep);
        editTexts[12]= findViewById(R.id.tvConesDep);
        editTexts[13]= findViewById(R.id.tvConebDep);
        editTexts[15]= findViewById(R.id.tvPistaDep);
        editTexts[16]= findViewById(R.id.tvBonanzaDep);
        editTexts[17]= findViewById(R.id.tvFamilyDep);
        editTexts[18]= findViewById(R.id.tvFamily2Dep);
        editTexts[14]= findViewById(R.id.tvNuttyDep);

        editTextss[0] = findViewById(R.id.tvKacchasRet);
        editTextss[1]= findViewById(R.id.tvLitchisRet);
        editTextss[2] = findViewById(R.id.tvStrawsRet);
        editTextss[3]= findViewById(R.id.tvColasRet);
        editTextss[4] = findViewById(R.id.tvPinesRet);
        editTextss[5] = findViewById(R.id.tvOrangsRet);
        editTextss[6]= findViewById(R.id.tvMangosRet);
        editTextss[7]= findViewById(R.id.tvCupssRet);
        editTextss[8] = findViewById(R.id.tvCupbsRet);
        editTextss[9]= findViewById(R.id.tvChocossRet);
        editTextss[10]= findViewById(R.id.tvChocobsRet);
        editTextss[11]= findViewById(R.id.tvMatkasRet);
        editTextss[12]= findViewById(R.id.tvConessRet);
        editTextss[13]= findViewById(R.id.tvConebsRet);
        editTextss[15]= findViewById(R.id.tvPistasRet);
        editTextss[16]= findViewById(R.id.tvBonanzasRet);
        editTextss[17]= findViewById(R.id.tvFamilysRet);
        editTextss[18]= findViewById(R.id.tvFamily2sRet);
        editTextss[14]= findViewById(R.id.tvNuttysRet);


        textViews[0] = findViewById(R.id.tvTotal1s);
        textViews[1]= findViewById(R.id.tvTotal2s);
        textViews[2] = findViewById(R.id.tvTotal3s);
        textViews[3]= findViewById(R.id.tvTotal4s);
        textViews[4] = findViewById(R.id.tvTotal5s);
        textViews[5] = findViewById(R.id.tvTotal6s);
        textViews[6]= findViewById(R.id.tvTotal7s);
        textViews[7]= findViewById(R.id.tvTotal8s);
        textViews[8] = findViewById(R.id.tvTotal9s);
        textViews[9]= findViewById(R.id.tvTotal10s);
        textViews[10]= findViewById(R.id.tvTotal11s);
        textViews[11]= findViewById(R.id.tvTotal12s);
        textViews[12]= findViewById(R.id.tvTotal13s);
        textViews[13]= findViewById(R.id.tvTotal14s);
        textViews[14]= findViewById(R.id.tvTotal15s);
        textViews[15]= findViewById(R.id.tvTotal16s);
        textViews[16]= findViewById(R.id.tvTotal17s);
        textViews[17]= findViewById(R.id.tvTotal18s);
        textViews[18]= findViewById(R.id.tvTotal19s);
        tvTotal = findViewById(R.id.tvTot);
        Comm = findViewById(R.id.etComms);
        tvCommision = findViewById(R.id.tvCommisi);
        btnBills = findViewById(R.id.btnBills);
        tv = findViewById(R.id.second_edit_text);
        btnCTs = findViewById(R.id.btnCTs);
        tvDues = findViewById(R.id.tvDuess);
    }


}
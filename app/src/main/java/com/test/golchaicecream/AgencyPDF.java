package com.test.golchaicecream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import papaya.in.sendmail.SendMail;

public class AgencyPDF extends AppCompatActivity {




    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("record/Agency/"+fAuth.getCurrentUser().getUid());
    DatabaseReference mRef = database.getReference("record/AgencyLatest/");
    DatabaseReference adminRef = database.getReference("Golcha/Admin/");
    ProgressBar progressBar ;
    String loc;
    String name;



    Button btnsp,btnp;
    TextView[] textViewQ = new TextView[19];
//    TextView[] textViewT = new TextView[19];
    TextView tvName,tvAddress,tvDate,tvNotes;
    String userID = fAuth.getCurrentUser().getUid();;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    long invoiceNum;

    long invoice;
    String commision,total,dues;
    String senderEmail,senderPass,receiverEmail;
    DetailsObj detailsObj = new DetailsObj();

    String str[] ={"Kaccha Aaam","Litchi","Strawberry","Coca","PineApple","Orange Bar","Mango Bar","Cup-S","Cup-B","ChocoBar-S","ChocoBar-B","Matka","King Cone-S","King Cone-B","Family Pack(2 in 1)","Keshar Pista","Bonanza","Family Pack","Nutty Crunch"};
    String price[]={"250","250","250","250","250","250","250","150","240","300","360","300","420","500","120","240","200","100","400"};







    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_pdf);

        this.progressBar = findViewById(R.id.progressa);
        progressBar.setVisibility(View.VISIBLE);




        btnp = findViewById(R.id.btnp);
        btnsp = findViewById(R.id.btnsp);
        tvName=findViewById(R.id.invoice_name);
        tvAddress = findViewById(R.id.invoice_address);
        tvDate = findViewById(R.id.invoice_date);
        tvDate.setText(datePatternFormat.format(new Date().getTime()));


        initialisation();

        showalluserdata();






        fStore.collection("users").document(userID)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                             name= document.getString("fName");
                            String email = document.getString("email");
                            String num = document.getString("phone");
                            loc = document.getString("location");

                            tvAddress.setText(loc);
                            tvName.setText(name);


                        }
                    }else {

                    }
                });
        fStore.collection("Golcha").document("Admin")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            senderEmail = document.getString("email");
                            senderPass = document.getString("pass");
                            receiverEmail = document.getString("receiverEmail");
                            progressBar.setVisibility(View.GONE);
                        }
                    }else {

                    }
                });


        callOnClickListener();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoiceNum = dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoice = dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void initialisation() {

        textViewQ[0] = findViewById(R.id.tvQuantity1);
        textViewQ[1]= findViewById(R.id.tvQuantity2);
        textViewQ[2] = findViewById(R.id.tvQuantity3);
        textViewQ[3]= findViewById(R.id.tvQuantity4);
        textViewQ[4] = findViewById(R.id.tvQuantity5);
        textViewQ[5] = findViewById(R.id.tvQuantity6);
        textViewQ[6]= findViewById(R.id.tvQuantity7);
        textViewQ[7]= findViewById(R.id.tvQuantity8);
        textViewQ[8] = findViewById(R.id.tvQuantity9);
        textViewQ[9]= findViewById(R.id.tvQuantity10);
        textViewQ[10]= findViewById(R.id.tvQuantity11);
        textViewQ[11]= findViewById(R.id.tvQuantity12);
        textViewQ[12]= findViewById(R.id.tvQuantity13);
        textViewQ[13]= findViewById(R.id.tvQuantity14);
        textViewQ[14]= findViewById(R.id.tvQuantity15);
        textViewQ[15]= findViewById(R.id.tvQuantity16);
        textViewQ[16]= findViewById(R.id.tvQuantity17);
        textViewQ[18]= findViewById(R.id.tvQuantity19);
        textViewQ[17]= findViewById(R.id.tvQuantity18);


        tvNotes = findViewById(R.id.notes);




    }

    private void showalluserdata() {


        Intent i = getIntent();
        textViewQ[0].setText(i.getStringExtra("kacchaQty"));
        textViewQ[1].setText(i.getStringExtra("litchiQty"));
        textViewQ[2].setText(i.getStringExtra("strawQty"));
        textViewQ[3].setText(i.getStringExtra("colaQty"));
        textViewQ[4].setText(i.getStringExtra("pineQty"));
        textViewQ[5].setText(i.getStringExtra("orangeQty"));
        textViewQ[6].setText(i.getStringExtra("mangoQty"));
        textViewQ[7].setText(i.getStringExtra("cupSQty"));
        textViewQ[8].setText(i.getStringExtra("cupBQty"));
        textViewQ[9].setText(i.getStringExtra("chocoSQty"));
        textViewQ[10].setText(i.getStringExtra("chocoBQty"));
        textViewQ[11].setText(i.getStringExtra("matkaQty"));
        textViewQ[12].setText(i.getStringExtra("coneSQty"));
        textViewQ[13].setText(i.getStringExtra("coneBQty"));
        textViewQ[15].setText(i.getStringExtra("keshaerQty"));
        textViewQ[16].setText(i.getStringExtra("bonanzaQty"));
        textViewQ[17].setText(i.getStringExtra("familyQty"));
        textViewQ[14].setText(i.getStringExtra("nuttyQty"));
        textViewQ[18].setText(i.getStringExtra("family2Qty"));
        tvNotes.setText("Notes: "+ i.getStringExtra("Notes"));


    }

    private void callOnClickListener() {
        btnsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsObj.invoiceNo=invoiceNum+ 1;
                detailsObj.date = new Date().getTime();

                setDetailsOfObject();
                mRef.child(String.valueOf(invoice+1)).setValue(detailsObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Order Placed Successfully",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AgencyPDF.this,MainActivity.class);
                        String s = String.valueOf(invoice+1);
                        Toast.makeText(getApplicationContext(),senderEmail+senderPass,Toast.LENGTH_LONG).show();
                        SendMail mail = new SendMail(senderEmail, senderPass,
                                receiverEmail,
                                "You have a New Order ➺"+s,
                                "Dear Admin You have a new Order➺\n\n"+"Invoice Number➺"+s+" from\n Name➺"+name+"\nLocation➺"+loc+"\n\n☤ You can find Order Details from invoice Number➺"+s);

                        mail.execute();
                        startActivity(intent);
                        progressBar.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Check your network connection",Toast.LENGTH_LONG).show();

                    }
                });



            }


        });

    }

    private void setDetailsOfObject() {

        progressBar.setVisibility(View.VISIBLE);

        detailsObj.invoiceNo=invoiceNum+ 1;
        detailsObj.date = new Date().getTime();
        detailsObj.userId = userID;
        detailsObj.kacchaQty = Integer.parseInt(String.valueOf(textViewQ[0].getText()));
        detailsObj.litchiQty = Integer.parseInt(String.valueOf(textViewQ[1].getText()));
        detailsObj.strawQty = Integer.parseInt(String.valueOf(textViewQ[2].getText()));
        detailsObj.colaQty = Integer.parseInt(String.valueOf(textViewQ[3].getText()));
        detailsObj.pineQty = Integer.parseInt(String.valueOf(textViewQ[4].getText()));
        detailsObj.orangeQty = Integer.parseInt(String.valueOf(textViewQ[5].getText()));
        detailsObj.mangoQty = Integer.parseInt(String.valueOf(textViewQ[6].getText()));
        detailsObj.cupSQty = Integer.parseInt(String.valueOf(textViewQ[7].getText()));
        detailsObj.cupBQty = Integer.parseInt(String.valueOf(textViewQ[8].getText()));
        detailsObj.chocoSQty = Integer.parseInt(String.valueOf(textViewQ[9].getText()));
        detailsObj.chocoBQty = Integer.parseInt(String.valueOf(textViewQ[10].getText()));
        detailsObj.matkaQty = Integer.parseInt(String.valueOf(textViewQ[11].getText()));
        detailsObj.coneSQty = Integer.parseInt(String.valueOf(textViewQ[12].getText()));
        detailsObj.coneBQty = Integer.parseInt(String.valueOf(textViewQ[13].getText()));
        detailsObj.keshaerQty = Integer.parseInt(String.valueOf(textViewQ[15].getText()));
        detailsObj.bonanzaQty = Integer.parseInt(String.valueOf(textViewQ[16].getText()));
        detailsObj.familyQty= Integer.parseInt(String.valueOf(textViewQ[17].getText()));
        detailsObj.family2Qty= Integer.parseInt(String.valueOf(textViewQ[18].getText()));
        detailsObj.nuttyQty = Integer.parseInt(String.valueOf(textViewQ[14].getText()));


        String d = "0.0";


        detailsObj.total = Double.parseDouble(d);
        detailsObj.dues = Double.parseDouble(d);
        detailsObj.commision = Double.parseDouble(d);
        detailsObj.custName = tvName.getText().toString();


    }


}
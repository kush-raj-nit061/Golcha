package com.example.golchaicecream;

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
import android.widget.TextView;
import android.widget.Toast;
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

public class PDF_Activity extends AppCompatActivity {




    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("record/Agency/"+fAuth.getCurrentUser().getUid());



    Button btnsp,btnp;
    TextView[] textViewQ = new TextView[19];
    TextView[] textViewT = new TextView[19];
    TextView tvName,tvAddress,tvDate,tvComm,tvTota,tvDue,tvNotes;
    String userID = fAuth.getCurrentUser().getUid();;
    DataObj dataObj = new DataObj();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    long invoiceNum;
    DetailsObj detailsObj = new DetailsObj();

    String str[] ={"Kaccha Aaam","Litchi","Strawberry","Coca","PineApple","Orange Bar","Mango Bar","Cup-S","Cup-B","ChocoBar-S","ChocoBar-B","Matka","King Cone-S","King Cone-B","Family Pack(2 in 1)","Keshar Pista","Bonanza","Family Pack","Nutty Crunch"};
    String price[]={"250","250","250","250","250","250","250","150","240","300","360","300","420","500","120","240","200","100","400"};







    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

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
                                    String name = document.getString("fName");
                                    String email = document.getString("email");
                                    String num = document.getString("phone");
                                    String loc = document.getString("location");

                                    tvAddress.setText(loc);
                                    tvName.setText(name);


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

        textViewT[0] = findViewById(R.id.tvTota1);
        textViewT[1]= findViewById(R.id.tvTota2);
        textViewT[2] = findViewById(R.id.tvTota3);
        textViewT[3]= findViewById(R.id.tvTota4);
        textViewT[4] = findViewById(R.id.tvTota5);
        textViewT[5] = findViewById(R.id.tvTota6);
        textViewT[6]= findViewById(R.id.tvTota7);
        textViewT[7]= findViewById(R.id.tvTota8);
        textViewT[8] = findViewById(R.id.tvTota9);
        textViewT[9]= findViewById(R.id.tvTota10);
        textViewT[10]= findViewById(R.id.tvTota11);
        textViewT[11]= findViewById(R.id.tvTota12);
        textViewT[12]= findViewById(R.id.tvTota13);
        textViewT[13]= findViewById(R.id.tvTota14);
        textViewT[14]= findViewById(R.id.tvTota15);
        textViewT[15]= findViewById(R.id.tvTota16);
        textViewT[16]= findViewById(R.id.tvTota17);
        textViewT[18]= findViewById(R.id.tvTota19);
        textViewT[17]= findViewById(R.id.tvTota18);


        tvDue = findViewById(R.id.tvDue);
        tvComm= findViewById(R.id.tvComm);
        tvTota = findViewById(R.id.tvTota);
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
        textViewQ[14].setText(i.getStringExtra("family2Qty"));
        textViewQ[18].setText(i.getStringExtra("nuttyQty"));

        textViewT[0].setText(i.getStringExtra("kacchaPrice"));
        textViewT[1].setText(i.getStringExtra("litchiPrice"));
        textViewT[2].setText(i.getStringExtra("strawPrice"));
        textViewT[3].setText(i.getStringExtra("colaPrice"));
        textViewT[4].setText(i.getStringExtra("pinePrice"));
        textViewT[5].setText(i.getStringExtra("orangePrice"));
        textViewT[6].setText(i.getStringExtra("mangoPrice"));
        textViewT[7].setText(i.getStringExtra("cupSPrice"));
        textViewT[8].setText(i.getStringExtra("cupBPrice"));
        textViewT[9].setText(i.getStringExtra("chocoSPrice"));
        textViewT[10].setText(i.getStringExtra("chocoBPrice"));
        textViewT[11].setText(i.getStringExtra("matkaPrice"));
        textViewT[12].setText(i.getStringExtra("coneSPrice"));
        textViewT[13].setText(i.getStringExtra("coneBPrice"));
        textViewT[15].setText(i.getStringExtra("keshaerPrice"));
        textViewT[16].setText(i.getStringExtra("bonanzaPrice"));
        textViewT[17].setText(i.getStringExtra("familyPrice"));
        textViewT[14].setText(i.getStringExtra("family2Price"));
        textViewT[18].setText(i.getStringExtra("nuttyPrice"));

        tvNotes.setText("Notes: "+ i.getStringExtra("Notes"));


        tvTota.setText(i.getStringExtra("Total"));
        tvComm.setText(i.getStringExtra("Commission"));
        tvDue.setText(i.getStringExtra("Dues"));

    }

    private void callOnClickListener() {
        btnsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsObj.invoiceNo=invoiceNum+ 1;
                detailsObj.date = new Date().getTime();

                setDetailsOfObject();
                myRef.child(String.valueOf(invoiceNum+1)).setValue(detailsObj);
                printPdf();
            }


        });
        btnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PDF_Activity.this,MainActivity.class);





                startActivity(intent);
            }
        });
    }

    private void setDetailsOfObject() {

        detailsObj.invoiceNo=invoiceNum+ 1;
        detailsObj.date = new Date().getTime();
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
        detailsObj.family2Qty= Integer.parseInt(String.valueOf(textViewQ[14].getText()));
        detailsObj.nuttyQty = Integer.parseInt(String.valueOf(textViewQ[18].getText()));

        detailsObj.kacchaPrice = Integer.parseInt(String.valueOf(textViewT[0].getText()));
        detailsObj.litchiPrice = Integer.parseInt(String.valueOf(textViewT[1].getText()));
        detailsObj.strawPrice = Integer.parseInt(String.valueOf(textViewT[2].getText()));
        detailsObj.colaPrice = Integer.parseInt(String.valueOf(textViewT[3].getText()));
        detailsObj.pinePrice = Integer.parseInt(String.valueOf(textViewT[4].getText()));
        detailsObj.orangePrice = Integer.parseInt(String.valueOf(textViewT[5].getText()));
        detailsObj.mangoPrice = Integer.parseInt(String.valueOf(textViewT[6].getText()));
        detailsObj.cupSPrice = Integer.parseInt(String.valueOf(textViewT[7].getText()));
        detailsObj.cupBPrice = Integer.parseInt(String.valueOf(textViewT[8].getText()));
        detailsObj.chocoSPrice = Integer.parseInt(String.valueOf(textViewT[9].getText()));
        detailsObj.chocoBPrice = Integer.parseInt(String.valueOf(textViewT[10].getText()));
        detailsObj.matkaPrice = Integer.parseInt(String.valueOf(textViewT[11].getText()));
        detailsObj.coneSPrice = Integer.parseInt(String.valueOf(textViewT[12].getText()));
        detailsObj.coneBPrice = Integer.parseInt(String.valueOf(textViewT[13].getText()));
        detailsObj.keshaerPrice = Integer.parseInt(String.valueOf(textViewT[15].getText()));
        detailsObj.bonanzaPrice = Integer.parseInt(String.valueOf(textViewT[16].getText()));
        detailsObj.familyPrice = Integer.parseInt(String.valueOf(textViewT[17].getText()));
        detailsObj.family2Price = Integer.parseInt(String.valueOf(textViewT[14].getText()));
        detailsObj.nuttyPrice = Integer.parseInt(String.valueOf(textViewT[18].getText()));


        detailsObj.total = Double.parseDouble(String.valueOf(tvTota.getText()));
        detailsObj.dues = Double.parseDouble(String.valueOf(tvDue.getText()));
        detailsObj.commision = Double.parseDouble(String.valueOf(tvComm.getText()));
        detailsObj.custName = tvName.getText().toString();


    }

    private void printPdf() {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint forLinePaint =new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200,2600,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();
        paint.setTextSize(19.0f);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.banner);
        Bitmap scale = Bitmap.createScaledBitmap(bmp,1200,462,false);
        canvas.drawBitmap(scale,0,0,paint);
        paint.setTextSize(50);
        canvas.drawText("---------------------------------------------------------------------------------------------------------------------------------------",0,500,paint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.rgb(0,0,0));
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
        paint.setTextSize(70);
        canvas.drawText("Invoice",600,550,paint);
        paint.setTextSize(50);
        canvas.drawText("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",0,570,paint);
        paint.setTextSize(35);
        canvas.drawText("Date: "+datePatternFormat.format(new Date().getTime()),900,600,paint);
        canvas.drawText(tvName.getText().toString(),900,650,paint);
        canvas.drawText("Address: "+tvAddress.getText().toString(),900,700,paint);
        paint.setTextSize(70);
        canvas.drawText("---------------------------------------------------------------------------------------------------------------------------------------",0,750,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(20,780,1180,860,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        canvas.drawText("Sl.No",40,830,paint);
        canvas.drawText("Items Description",200,830,paint);
        canvas.drawText("Price",660,830,paint);
        canvas.drawText("Qty",900,830,paint);
        canvas.drawText("Total",1030,830,paint);
        canvas.drawLine(140,790,140,840,paint);
        canvas.drawLine(640,790,640,840,paint);
        canvas.drawLine(840,790,840,840,paint);
        canvas.drawLine(1000,790,1000,840,paint);
        int position=950;
        int num=1;
        for(int i = 0;i<19;i++){
            if(!textViewT[i].getText().toString().equals("0")){
                canvas.drawText(String.valueOf(num),40,position,paint);
                canvas.drawText(str[i],160,position,paint);
                canvas.drawText(price[i],670,position,paint);
                canvas.drawText(textViewQ[i].getText().toString(),900,position,paint);
                canvas.drawText(textViewT[i].getText().toString(),1040,position,paint);

                position+=60;
                num++;
            }
        }
        canvas.drawLine(680,position+100,1180,position+100,paint);
        canvas.drawText("Sub-Total",700,position+150,paint);
        canvas.drawText(":",900,position+150,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(tvTota.getText().toString(),1160,position+150,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Commission",700,position+200,paint);
        canvas.drawText(":",900,position+200,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(tvComm.getText().toString(),1160,position+200,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Total",700,position+250,paint);
        canvas.drawText(":",900,position+250,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(tvDue.getText().toString(),1160,position+250,paint);
        canvas.drawLine(680,position+300,1180,position+300,paint);
        paint.setTextSize(40);
        paint.setColor(Color.RED);
        canvas.drawText(tvNotes.getText().toString(),1160,position+15,paint);
        myPdfDocument.finishPage(myPage);
        String directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+Long.toString(invoiceNum)+".pdf";
        File filePath = new File(targetPdf);
        try {
            myPdfDocument.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PDF_Activity.this,MainActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        myPdfDocument.close();

    }
}
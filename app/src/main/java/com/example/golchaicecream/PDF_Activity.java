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
import android.widget.Toast;

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

public class PDF_Activity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("record");
    Button btnsp,btnp;
    EditText etn,etnum;
    Spinner sp;
    String itemlist[];
    double[] itemPrice;
    ArrayAdapter<String> adapter;
    DataObj dataObj = new DataObj();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    long invoiceNum;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        itemlist = new String[]{"petrol","diesel"};
        itemPrice = new double[]{61.44,44.92};

        btnp = findViewById(R.id.btnPrint);
        btnsp = findViewById(R.id.btnSaveAndPrint);
        etn = findViewById(R.id.editTextName);
        etnum = findViewById(R.id.editTextQty);
        sp = findViewById(R.id.spinner);

        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,itemlist);
        sp.setAdapter(adapter);

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

    private void callOnClickListener() {
        btnsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataObj.invoiceNo=invoiceNum+ 1;
                myRef.child(String.valueOf(invoiceNum+1)).setValue(dataObj);
                dataObj.customerName = String.valueOf(etn.getText());
                dataObj.date = new Date().getTime();
                dataObj.fuelType = sp.getSelectedItem().toString();
                dataObj.fuelQty = Double.parseDouble(String.valueOf(etnum.getText()));
                dataObj.amount = Double.valueOf(decimalFormat.format(dataObj.getFuelQty()*itemPrice[sp.getSelectedItemPosition()]));
                printPdf();
            }


        });
        btnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PDF_Activity.this,OldPrintActivity.class);
                startActivity(intent);
            }
        });
    }
    private void printPdf() {

        PdfDocument myPdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint forLinePaint =new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,350,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0,50,250));

        canvas.drawText("GOLCHA ICECREAM",20,20,paint);
        paint.setTextSize(8.5f);
        canvas.drawText("Aurangabad road, Daudnagar",20,40,paint);
        canvas.drawText("Daudangar 824113",20,40,paint);
        forLinePaint.setStyle(Paint.Style.STROKE);
        forLinePaint.setPathEffect(new DashPathEffect(new float[]{5,5},0));

        canvas.drawLine(20,65,230,65,forLinePaint);

        canvas.drawText("Customer Name" + etn.getText(),20,60,paint);
        canvas.drawLine(20, 90,230,90,forLinePaint);

        canvas.drawText("Purchase",20,105,paint);
        canvas.drawText("Date"+datePatternFormat.format(new Date().getTime()),20,260,paint);

        myPdfDocument.finishPage(myPage);
        File file = new File(this.getExternalFilesDir("/"),"invoice.pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        }catch (IOException e){
            e.printStackTrace();
        }

        myPdfDocument.close();

    }
}
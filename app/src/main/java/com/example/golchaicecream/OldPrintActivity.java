package com.example.golchaicecream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.ArrayList;
import java.util.Date;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class OldPrintActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("record");
    DatabaseReference retriveRef;
    Button oldPrintBtn;
    EditText oldPrintEt;
    DataTable dataTable;
    DataTableHeader header;
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-mm-yyyy");
    SimpleDateFormat timePatternFormat =new SimpleDateFormat("hh:mm a");

    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    ArrayList<DataTableRow> rows = new ArrayList<>();

    long invoiceNo;
    String customerName;
    long date;
    String fuelType;
    Double fuelQty;
    Double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_print);

        oldPrintBtn = findViewById(R.id.oldPrintBtn);
        oldPrintEt = findViewById(R.id.oldPrintEditText);
        dataTable = findViewById(R.id.data_table);

        header = new DataTableHeader.Builder()
                .item("Invoice No",5)
                .item("Customer Name", 5)
                .item("Date",5)
                .item("Time",5)
                .item("Amount",5)
                .build();
        loadTable();
        listeners();
    }

    private void listeners() {
        oldPrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printPdf(oldPrintEt.getText().toString());
            }
        });
    }

    private void printPdf(String invoice) {
        //ambiguity chance
        retriveRef = FirebaseDatabase.getInstance().getReference().child("record").child(invoice);
        retriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invoiceNo = (long) snapshot.child("invoiceNo").getValue();
                customerName=(String) snapshot.child("customerName").getValue();
                date =(long) snapshot.child("date").getValue();
                fuelType = (String) snapshot.child("fuelType").getValue();
                fuelQty = Double.parseDouble(String.valueOf(snapshot.child("fuelQty").getValue()));
                amount = Double.parseDouble(String.valueOf(snapshot.child("amount").getValue()));

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

                canvas.drawText("Customer Name"+customerName ,20,60,paint);
                canvas.drawLine(20, 90,230,90,forLinePaint);

                canvas.drawText("Purchase",20,105,paint);
                canvas.drawText("Date"+datePatternFormat.format(new Date().getTime()),20,260,paint);

                myPdfDocument.finishPage(myPage);
                File file = new File(OldPrintActivity.this.getExternalFilesDir("/"), invoiceNo+"invoice.pdf");

                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));
                }catch (IOException e){
                    e.printStackTrace();
                }

                myPdfDocument.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadTable() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {
                   DataTableRow row = new DataTableRow.Builder()
                           .value(String.valueOf(myDataSnapshot.child("invoiceNo").getValue()))
                           .value(String.valueOf(myDataSnapshot.child("customerName").getValue()))
                           .value(datePatternFormat.format(myDataSnapshot.child("date").getValue()))
                           .value(timePatternFormat.format(myDataSnapshot.child("date").getValue()))
                           .value(String.valueOf(myDataSnapshot.child("amount").getValue()))
                           .build();
                   rows.add(row);
               }
               dataTable.setHeader(header);
               dataTable.setRows(rows);
               dataTable.inflate(OldPrintActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.golchaicecream;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class OldPrintActivity extends AppCompatActivity {
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference("record/Agency/"+fAuth.getCurrentUser().getUid());
    DatabaseReference retriveRef;
    StorageReference storageReference;
    Button oldPrintBtn;
    EditText oldPrintEt;
    DataTable dataTable;
    DataTableHeader header;
    Intent in = null;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat timePatternFormat =new SimpleDateFormat("hh:mm a");

    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    ArrayList<DataTableRow> rows = new ArrayList<>();


    String customerName;
    String loc;

    String userID = fAuth.getCurrentUser().getUid();
    String str[] ={"Kaccha Aaam","Litchi","Strawberry","Coca","PineApple","Orange Bar","Mango Bar","Cup-S","Cup-B","ChocoBar-S","ChocoBar-B","Matka","King Cone-S","King Cone-B","Family Pack(2 in 1)","Keshar Pista","Bonanza","Family Pack","Nutty Crunch"};
    String price[]={"250","250","250","250","250","250","250","150","240","300","360","300","420","500","120","240","200","100","400"};


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_print);



        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+ Objects.requireNonNull(fAuth.getCurrentUser()).getUid()+"recent.pdf");

        oldPrintBtn = findViewById(R.id.oldPrintBtn);
        oldPrintEt = findViewById(R.id.oldPrintEditText);
        dataTable = findViewById(R.id.data_table_a);

        header = new DataTableHeader.Builder()
                .item("Invoice No",5)
                .item("Customer Name", 8)
                .item("Date",5)
                .item("Time",5)
                .item("Amount",5)
                .build();
        loadTable();
        listeners();
    }

    private void listeners() {
        fStore.collection("users").document(userID)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            customerName = document.getString("fName");
                            String email = document.getString("email");
                            String num = document.getString("phone");
                             loc = document.getString("location");


                        }
                    }else {

                    }
                });

        oldPrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printPdf(oldPrintEt.getText().toString());
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
                            .value(String.valueOf(myDataSnapshot.child("custName").getValue()))
                            .value(datePatternFormat.format(myDataSnapshot.child("date").getValue()))
                            .value(timePatternFormat.format(myDataSnapshot.child("date").getValue()))
                            .value(String.valueOf(myDataSnapshot.child("dues").getValue()))
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

    private void printPdf(String invoice) {
        //ambiguity chance
        retriveRef = FirebaseDatabase.getInstance().getReference().child("record/Agency/"+fAuth.getCurrentUser().getUid()).child(invoice);
        retriveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String invoiceNo = String.valueOf((long) snapshot.child("invoiceNo").getValue());

                Double amount = Double.parseDouble(String.valueOf(snapshot.child("dues").getValue()));
                Double tvTota = Double.parseDouble(String.valueOf(snapshot.child("total").getValue()));
                Double tvComm = Double.parseDouble(String.valueOf(snapshot.child("commision").getValue()));


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
                String date =String.valueOf((long) snapshot.child("date").getValue());
                canvas.drawText("Date: "+datePatternFormat.format(new Date(Long.valueOf(date)).getTime()),900,600,paint);
                canvas.drawText(customerName,900,650,paint);
                canvas.drawText("Invoice No:"+invoice,200,650,paint);
                canvas.drawText("Address: "+loc,900,700,paint);
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
                String strQ[]= new String[19];
                String strT[]= new String[19];
                strQ[0]=String.valueOf((long) snapshot.child("kacchaQty").getValue());
                strT[0]=String.valueOf((long) snapshot.child("kacchaPrice").getValue());
                strQ[1]=String.valueOf((long) snapshot.child("litchiQty").getValue());
                strT[1]=String.valueOf((long) snapshot.child("litchiPrice").getValue());
                strQ[2]=String.valueOf((long) snapshot.child("strawQty").getValue());
                strT[2]=String.valueOf((long) snapshot.child("strawPrice").getValue());
                strQ[3]=String.valueOf((long) snapshot.child("colaQty").getValue());
                strT[3]=String.valueOf((long) snapshot.child("colaPrice").getValue());
                strQ[4]=String.valueOf((long) snapshot.child("pineQty").getValue());
                strT[4]=String.valueOf((long) snapshot.child("pinePrice").getValue());
                strQ[5]=String.valueOf((long) snapshot.child("orangeQty").getValue());
                strT[5]=String.valueOf((long) snapshot.child("orangePrice").getValue());
                strQ[6]=String.valueOf((long) snapshot.child("mangoQty").getValue());
                strT[6]=String.valueOf((long) snapshot.child("mangoPrice").getValue());
                strQ[7]=String.valueOf((long) snapshot.child("cupSQty").getValue());
                strT[7]=String.valueOf((long) snapshot.child("cupSPrice").getValue());
                strQ[8]=String.valueOf((long) snapshot.child("cupBQty").getValue());
                strT[8]=String.valueOf((long) snapshot.child("cupBPrice").getValue());
                strQ[9]=String.valueOf((long) snapshot.child("chocoSQty").getValue());
                strT[9]=String.valueOf((long) snapshot.child("chocoSPrice").getValue());
                strQ[10]=String.valueOf((long) snapshot.child("chocoBQty").getValue());
                strT[10]=String.valueOf((long) snapshot.child("chocoBPrice").getValue());
                strQ[11]=String.valueOf((long) snapshot.child("matkaQty").getValue());
                strT[11]=String.valueOf((long) snapshot.child("matkaPrice").getValue());
                strQ[12]=String.valueOf((long) snapshot.child("coneSQty").getValue());
                strT[12]=String.valueOf((long) snapshot.child("coneSPrice").getValue());
                strQ[13]=String.valueOf((long) snapshot.child("coneBQty").getValue());
                strT[13]=String.valueOf((long) snapshot.child("coneBPrice").getValue());
                strQ[14]=String.valueOf((long) snapshot.child("family2Qty").getValue());
                strT[14]=String.valueOf((long) snapshot.child("family2Price").getValue());
                strQ[15]=String.valueOf((long) snapshot.child("keshaerQty").getValue());
                strT[15]=String.valueOf((long) snapshot.child("keshaerPrice").getValue());
                strQ[16]=String.valueOf((long) snapshot.child("bonanzaQty").getValue());
                strT[16]=String.valueOf((long) snapshot.child("bonanzaPrice").getValue());
                strQ[17]=String.valueOf((long) snapshot.child("familyQty").getValue());
                strT[17]=String.valueOf((long) snapshot.child("familyPrice").getValue());
                strQ[18]=String.valueOf((long) snapshot.child("nuttyQty").getValue());
                strT[18]=String.valueOf((long) snapshot.child("nuttyPrice").getValue());

                for(int i = 0;i<19;i++){
                    if(!(strT[i].equals("0"))) {
                        canvas.drawText(String.valueOf(num), 40, position, paint);
                        canvas.drawText(str[i], 160, position, paint);
                        canvas.drawText(price[i], 670, position, paint);
                        canvas.drawText(String.valueOf(strQ[i]), 900, position, paint);
                        canvas.drawText(String.valueOf(strT[i]), 1040, position, paint);
                        position += 60;
                        num++;
                    }

                }
                canvas.drawLine(680,position+100,1180,position+100,paint);
                canvas.drawText("Sub-Total",700,position+150,paint);
                canvas.drawText(":",900,position+150,paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(tvTota),1160,position+150,paint);
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Commission",700,position+200,paint);
                canvas.drawText(":",900,position+200,paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(tvComm),1160,position+200,paint);
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Total",700,position+250,paint);
                canvas.drawText(":",900,position+250,paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(String.valueOf(amount),1160,position+250,paint);
                canvas.drawLine(680,position+300,1180,position+300,paint);
                paint.setTextSize(40);
                paint.setColor(Color.RED);

                myPdfDocument.finishPage(myPage);
                String directory_path = Environment.getExternalStorageDirectory() + "/Golcha/mypdf/";


                File file = new File(directory_path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String targetPdf = directory_path+invoice+".pdf";
                File filePath = new File(targetPdf);
                Uri uri = Uri.fromFile(filePath);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                    if(!Environment.isExternalStorageManager()){
                        try {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                            startActivityIfNeeded(intent,101);
                        }catch (Exception e){
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                            startActivityIfNeeded(intent,101);
                            Toast.makeText(OldPrintActivity.this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
                        }
                    }else {
                        try {
                            myPdfDocument.writeTo(new FileOutputStream(filePath));
                            Toast.makeText(OldPrintActivity.this, "File Request is on way :)", Toast.LENGTH_SHORT).show();
                            uploadPDFtoFirebase(uri);
                        } catch (IOException e) {
                            Toast.makeText(OldPrintActivity.this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
                        }

                    }
                }





//                    Intent intent = new Intent(OldPrintActivity.this,MainActivity.class);
//                    startActivity(intent);

//                catch (IOException e) {
//                    Log.e("main", "error "+e.toString());
//                    Toast.makeText(OldPrintActivity.this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
//                }
                myPdfDocument.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadPDFtoFirebase(Uri uri) {
        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"recent.pdf");
        fileRef.putFile((uri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();


                        downloadFile(OldPrintActivity.this,"Golcha",".pdf",Environment.getExternalStorageDirectory()+"/",url);
                        in = new Intent(getApplicationContext(),MainActivity.class);
                        in.putExtra("url",url);
                        startActivity(in);
                        Toast.makeText(getApplicationContext(),"Downloaded",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"⬇Requested PDF",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OldPrintActivity.this,"Failed.",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri =Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        Toast.makeText(getApplicationContext(),"Invoice Downloading.....",Toast.LENGTH_SHORT).show();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,fileName+fileExtension);
        downloadManager.enqueue(request);

    }


}
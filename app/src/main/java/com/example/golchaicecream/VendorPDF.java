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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
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

public class VendorPDF extends AppCompatActivity {




//    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
//    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//    FirebaseAuth fAuth = FirebaseAuth.getInstance();
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    String ids,inv;
//    DatabaseReference myRef = database.getReference("record/Agency/"+fAuth.getCurrentUser().getUid());
//    DatabaseReference mRef;



    Button btnSI,btnp;
    TextView[] textViewQD = new TextView[19];
    TextView[] textViewQR = new TextView[19];
    TextView[] textViewP = new TextView[19];

    TextView[] textViewT = new TextView[19];
    TextView tvName,tvAddress,tvDate,tvComm,tvTota,tvDue,tvNotes;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    long invoiceNum;
//    DetailsObjVendor detailsObj = new DetailsObjVendor();
    String custName;

    String str[] ={"Kaccha Aaam","Litchi","Strawberry","Coca","PineApple","Orange Bar","Mango Bar","Cup-S","Cup-B","ChocoBar-S","ChocoBar-B","Matka","King Cone-S","King Cone-B","Nutty Crunch","Keshar Pista","Bonanza","Family Pack","Family Pack(2in1)"};








    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_pdf);

        btnp = findViewById(R.id.btnp);
        btnSI = findViewById(R.id.btnSI);
        tvName=findViewById(R.id.vendorNames);
        tvAddress = findViewById(R.id.vendorAddresss);
        tvDate = findViewById(R.id.invoice_date);
        tvDate.setText(datePatternFormat.format(new Date().getTime()));

        initialisation();

        showalluserdata();

        callOnClickListener();

    }

    private void callOnClickListener() {

        btnSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printPdf();

            }
        });


    }



    @SuppressLint("SetTextI18n")
    private void showalluserdata() {


        Intent i = getIntent();
        textViewP[0].setText("₹"+i.getStringExtra("price1"));
        textViewP[1].setText("₹"+i.getStringExtra("price2"));
        textViewP[2].setText("₹"+i.getStringExtra("price3"));
        textViewP[3].setText("₹"+i.getStringExtra("price4"));
        textViewP[4].setText("₹"+i.getStringExtra("price5"));
        textViewP[5].setText("₹"+i.getStringExtra("price6"));
        textViewP[6].setText("₹"+i.getStringExtra("price7"));
        textViewP[7].setText("₹"+i.getStringExtra("price8"));
        textViewP[8].setText("₹"+i.getStringExtra("price9"));
        textViewP[9].setText("₹"+i.getStringExtra("price10"));
        textViewP[10].setText("₹"+i.getStringExtra("price11"));
        textViewP[11].setText("₹"+i.getStringExtra("price12"));
        textViewP[12].setText("₹"+i.getStringExtra("price13"));
        textViewP[13].setText("₹"+i.getStringExtra("price14"));
        textViewP[14].setText("₹"+i.getStringExtra("price15"));
        textViewP[15].setText("₹"+i.getStringExtra("price16"));
        textViewP[16].setText("₹"+i.getStringExtra("price17"));
        textViewP[17].setText("₹"+i.getStringExtra("price18"));
        textViewP[18].setText("₹"+i.getStringExtra("price19"));


        textViewQD[0].setText(i.getStringExtra("kacchaQty"));
        textViewQD[1].setText(i.getStringExtra("litchiQty"));
        textViewQD[2].setText(i.getStringExtra("strawQty"));
        textViewQD[3].setText(i.getStringExtra("colaQty"));
        textViewQD[4].setText(i.getStringExtra("pineQty"));
        textViewQD[5].setText(i.getStringExtra("orangeQty"));
        textViewQD[6].setText(i.getStringExtra("mangoQty"));
        textViewQD[7].setText(i.getStringExtra("cupSQty"));
        textViewQD[8].setText(i.getStringExtra("cupBQty"));
        textViewQD[9].setText(i.getStringExtra("chocoSQty"));
        textViewQD[10].setText(i.getStringExtra("chocoBQty"));
        textViewQD[11].setText(i.getStringExtra("matkaQty"));
        textViewQD[12].setText(i.getStringExtra("coneSQty"));
        textViewQD[13].setText(i.getStringExtra("coneBQty"));
        textViewQD[15].setText(i.getStringExtra("keshaerQty"));
        textViewQD[16].setText(i.getStringExtra("bonanzaQty"));
        textViewQD[17].setText(i.getStringExtra("familyQty"));
        textViewQD[18].setText(i.getStringExtra("family2Qty"));
        textViewQD[14].setText(i.getStringExtra("nuttyQty"));


        textViewQR[0].setText(i.getStringExtra("kacchaQtyR"));
        textViewQR[1].setText(i.getStringExtra("litchiQtyR"));
        textViewQR[2].setText(i.getStringExtra("strawQtyR"));
        textViewQR[3].setText(i.getStringExtra("colaQtyR"));
        textViewQR[4].setText(i.getStringExtra("pineQtyR"));
        textViewQR[5].setText(i.getStringExtra("orangeQtyR"));
        textViewQR[6].setText(i.getStringExtra("mangoQtyR"));
        textViewQR[7].setText(i.getStringExtra("cupSQtyR"));
        textViewQR[8].setText(i.getStringExtra("cupBQtyR"));
        textViewQR[9].setText(i.getStringExtra("chocoSQtyR"));
        textViewQR[10].setText(i.getStringExtra("chocoBQtyR"));
        textViewQR[11].setText(i.getStringExtra("matkaQtyR"));
        textViewQR[12].setText(i.getStringExtra("coneSQtyR"));
        textViewQR[13].setText(i.getStringExtra("coneBQtyR"));
        textViewQR[15].setText(i.getStringExtra("keshaerQtyR"));
        textViewQR[16].setText(i.getStringExtra("bonanzaQtyR"));
        textViewQR[17].setText(i.getStringExtra("familyQtyR"));
        textViewQR[18].setText(i.getStringExtra("family2QtyR"));
        textViewQR[14].setText(i.getStringExtra("nuttyQtyR"));

        textViewT[0].setText("₹"+i.getStringExtra("kacchaPrice"));
        textViewT[1].setText("₹"+i.getStringExtra("litchiPrice"));
        textViewT[2].setText("₹"+i.getStringExtra("strawPrice"));
        textViewT[3].setText("₹"+i.getStringExtra("colaPrice"));
        textViewT[4].setText("₹"+i.getStringExtra("pinePrice"));
        textViewT[5].setText("₹"+i.getStringExtra("orangePrice"));
        textViewT[6].setText("₹"+i.getStringExtra("mangoPrice"));
        textViewT[7].setText("₹"+i.getStringExtra("cupSPrice"));
        textViewT[8].setText("₹"+i.getStringExtra("cupBPrice"));
        textViewT[9].setText("₹"+i.getStringExtra("chocoSPrice"));
        textViewT[10].setText("₹"+i.getStringExtra("chocoBPrice"));
        textViewT[11].setText("₹"+i.getStringExtra("matkaPrice"));
        textViewT[12].setText("₹"+i.getStringExtra("coneSPrice"));
        textViewT[13].setText("₹"+i.getStringExtra("coneBPrice"));
        textViewT[15].setText("₹"+i.getStringExtra("keshaerPrice"));
        textViewT[16].setText("₹"+i.getStringExtra("bonanzaPrice"));
        textViewT[17].setText("₹"+i.getStringExtra("familyPrice"));
        textViewT[18].setText("₹"+i.getStringExtra("family2Price"));
        textViewT[14].setText("₹"+i.getStringExtra("nuttyPrice"));

        tvNotes.setText("Notes: "+ i.getStringExtra("Notes"));
        tvName.setText(i.getStringExtra("custName"));
        tvAddress.setText(i.getStringExtra("custAddress"));


        tvTota.setText(i.getStringExtra("Total"));
        tvComm.setText(i.getStringExtra("Commission"));
        tvDue.setText(i.getStringExtra("Dues"));
        custName = i.getStringExtra( "custName");

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
        canvas.drawText("Name:"+tvName.getText().toString(),900,650,paint);
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
        canvas.drawText("Items Description",180,830,paint);
        canvas.drawText("Price",550,830,paint);
        canvas.drawText("Depart",700,830,paint);
        canvas.drawText("Return",860,830,paint);
        canvas.drawText("Total",1030,830,paint);
        canvas.drawLine(140,790,140,840,paint);
        canvas.drawLine(520,790,520,840,paint);
        canvas.drawLine(680,790,680,840,paint);
        canvas.drawLine(840,790,840,840,paint);
        canvas.drawLine(1000,790,1000,840,paint);
        int position=950;
        int num=1;
        for(int i = 0;i<19;i++){
            if(!textViewT[i].getText().toString().equals("0")){
                canvas.drawText(String.valueOf(num),40,position,paint);
                canvas.drawText(str[i],180,position,paint);
                canvas.drawText(textViewP[i].getText().toString(),565,position,paint);
                canvas.drawText(textViewQD[i].getText().toString(),725,position,paint);
                canvas.drawText(textViewQR[i].getText().toString(),890,position,paint);

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
        String s = datePatternFormat.format(new Date());
        String p = s.substring(0,13)+"-"+s.substring(14,19);
        String q = s.substring(0,11);
        String directory_path = Environment.getExternalStorageDirectory() + "/Golcha/mypdf/Vendor/"+q+"/";


        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String targetPdf = directory_path+custName+"_"+p+".pdf";
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
                    Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
                }
            }else {
                try {
                    myPdfDocument.writeTo(new FileOutputStream(filePath));
                    Toast.makeText(this, "File Saved (Golcha/mypdf/Vendor/"+q+")", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
                }

            }
        }
        myPdfDocument.close();

    }




    private void initialisation() {


        textViewP[0]=findViewById(R.id.prices1);
        textViewP[1]=findViewById(R.id.prices2);
        textViewP[2]=findViewById(R.id.prices3);
        textViewP[3]=findViewById(R.id.prices4);
        textViewP[4]=findViewById(R.id.prices5);
        textViewP[5]=findViewById(R.id.prices6);
        textViewP[6]=findViewById(R.id.prices7);
        textViewP[7]=findViewById(R.id.prices8);
        textViewP[8]=findViewById(R.id.prices9);
        textViewP[9]=findViewById(R.id.prices10);
        textViewP[10]=findViewById(R.id.prices11);
        textViewP[11]=findViewById(R.id.prices12);
        textViewP[12]=findViewById(R.id.prices13);
        textViewP[13]=findViewById(R.id.prices14);
        textViewP[14]=findViewById(R.id.prices15);
        textViewP[15]=findViewById(R.id.prices16);
        textViewP[16]=findViewById(R.id.prices17);
        textViewP[17]=findViewById(R.id.prices18);
        textViewP[18]=findViewById(R.id.prices19);

        textViewQD[0] = findViewById(R.id.tvQuantity1);
        textViewQD[1]= findViewById(R.id.tvQuantity2);
        textViewQD[2] = findViewById(R.id.tvQuantity3);
        textViewQD[3]= findViewById(R.id.tvQuantity4);
        textViewQD[4] = findViewById(R.id.tvQuantity5);
        textViewQD[5] = findViewById(R.id.tvQuantity6);
        textViewQD[6]= findViewById(R.id.tvQuantity7);
        textViewQD[7]= findViewById(R.id.tvQuantity8);
        textViewQD[8] = findViewById(R.id.tvQuantity9);
        textViewQD[9]= findViewById(R.id.tvQuantity10);
        textViewQD[10]= findViewById(R.id.tvQuantity11);
        textViewQD[11]= findViewById(R.id.tvQuantity12);
        textViewQD[12]= findViewById(R.id.tvQuantity13);
        textViewQD[13]= findViewById(R.id.tvQuantity14);
        textViewQD[14]= findViewById(R.id.tvQuantity15);
        textViewQD[15]= findViewById(R.id.tvQuantity16);
        textViewQD[16]= findViewById(R.id.tvQuantity17);
        textViewQD[18]= findViewById(R.id.tvQuantity19);
        textViewQD[17]= findViewById(R.id.tvQuantity18);


        textViewQR[0] = findViewById(R.id.tvQuantitys1);
        textViewQR[1]= findViewById(R.id.tvQuantitys2);
        textViewQR[2] = findViewById(R.id.tvQuantitys3);
        textViewQR[3]= findViewById(R.id.tvQuantitys4);
        textViewQR[4] = findViewById(R.id.tvQuantitys5);
        textViewQR[5] = findViewById(R.id.tvQuantitys6);
        textViewQR[6]= findViewById(R.id.tvQuantitys7);
        textViewQR[7]= findViewById(R.id.tvQuantitys8);
        textViewQR[8] = findViewById(R.id.tvQuantitys9);
        textViewQR[9]= findViewById(R.id.tvQuantitys10);
        textViewQR[10]= findViewById(R.id.tvQuantitys11);
        textViewQR[11]= findViewById(R.id.tvQuantitys12);
        textViewQR[12]= findViewById(R.id.tvQuantitys13);
        textViewQR[13]= findViewById(R.id.tvQuantitys14);
        textViewQR[14]= findViewById(R.id.tvQuantitys15);
        textViewQR[15]= findViewById(R.id.tvQuantitys16);
        textViewQR[16]= findViewById(R.id.tvQuantitys17);
        textViewQR[18]= findViewById(R.id.tvQuantitys19);
        textViewQR[17]= findViewById(R.id.tvQuantitys18);

        textViewT[0] = findViewById(R.id.tvTot1);
        textViewT[1]= findViewById(R.id.tvTot2);
        textViewT[2] = findViewById(R.id.tvTot3);
        textViewT[3]= findViewById(R.id.tvTot4);
        textViewT[4] = findViewById(R.id.tvTot5);
        textViewT[5] = findViewById(R.id.tvTot6);
        textViewT[6]= findViewById(R.id.tvTot7);
        textViewT[7]= findViewById(R.id.tvTot8);
        textViewT[8] = findViewById(R.id.tvTot9);
        textViewT[9]= findViewById(R.id.tvTot10);
        textViewT[10]= findViewById(R.id.tvTot11);
        textViewT[11]= findViewById(R.id.tvTot12);
        textViewT[12]= findViewById(R.id.tvTot13);
        textViewT[13]= findViewById(R.id.tvTot14);
        textViewT[14]= findViewById(R.id.tvTot15);
        textViewT[15]= findViewById(R.id.tvTot16);
        textViewT[16]= findViewById(R.id.tvTot17);
        textViewT[18]= findViewById(R.id.tvTot19);
        textViewT[17]= findViewById(R.id.tvTot18);

        tvName = findViewById(R.id.vendorNames);
        tvAddress = findViewById(R.id.vendorAddresss);


        tvDue = findViewById(R.id.tvDues);
        tvComm= findViewById(R.id.tvComms);
        tvTota = findViewById(R.id.tvTotas);
        tvNotes = findViewById(R.id.notes);




    }
}
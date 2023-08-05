package com.example.golchaicecream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class AdminMainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference("record/AgencyLatest/");;

    StorageReference storageReference;
    Button reviewInvoices;
    EditText invoiceNumber;
    DataTable dataTable;
    DataTableHeader header;
    ProgressBar progressBar ;
    int invoice;
    String uid;

    FirebaseDatabase databases = FirebaseDatabase.getInstance();
    DatabaseReference myRefs = databases.getReference("record/AgencyLatest");

    int noOfOrders;



    Intent in = null;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormats = new SimpleDateFormat("dd-MM-yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat timePatternFormats =new SimpleDateFormat("hh:mm a");

    ArrayList<DataTableRow> rows = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        storageReference = FirebaseStorage.getInstance().getReference();
        this.progressBar = findViewById(R.id.progresswa);
        progressBar.setVisibility(View.VISIBLE);




        reviewInvoices = findViewById(R.id.reviewInvoices);
        invoiceNumber = findViewById(R.id.oldPrintEditText);
        dataTable = findViewById(R.id.data_table_a);

        header = new DataTableHeader.Builder()
                .item("InvNo.",3)
                .item("Name", 7)
                .item("Date",5)
                .item("Time",4)
                .item("UserId",5)
                .build();
        loadTable();
        listeners();
    }

    private void loadTable() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invoice = 0;
                for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {
                    String str = String.valueOf(myDataSnapshot.child("userId").getValue());
                    DataTableRow row = new DataTableRow.Builder()
                            .value(String.valueOf(invoice+1))
                            .value(String.valueOf(myDataSnapshot.child("custName").getValue()))
                            .value(datePatternFormats.format(myDataSnapshot.child("date").getValue()))
                            .value(timePatternFormats.format(myDataSnapshot.child("date").getValue()))
                            .value(str.substring(0,7))
                            .build();
                    rows.add(row);
                    invoice++;
                }
                dataTable.setHeader(header);
                dataTable.setRows(rows);
                dataTable.inflate(AdminMainActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void listeners() {


        myRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noOfOrders = (int) dataSnapshot.getChildrenCount();
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        reviewInvoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = invoiceNumber.getText().toString();

                if(s.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                                    "Please enter invoice number",
                                    Toast.LENGTH_SHORT)
                            .show();
                }else {

                    if(Integer.parseInt(s)<=noOfOrders && Integer.parseInt(s)>= 1){
                        Intent intent = new Intent(getApplicationContext(),Menu_Activity.class);
                        intent.putExtra("inv",s);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(),"Enter Valid Invoice No.",Toast.LENGTH_SHORT).show();
                    }



                }



            }
        });
    }

}
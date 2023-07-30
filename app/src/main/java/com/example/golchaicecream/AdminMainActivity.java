package com.example.golchaicecream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference("record/AgencyLatest/");;
    DatabaseReference retriveRef;
    StorageReference storageReference;
    Button reviewInvoices;
    EditText invoiceNumber;
    DataTable dataTable;
    DataTableHeader header;
    int invoice;
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




        reviewInvoices = findViewById(R.id.reviewInvoices);
        invoiceNumber = findViewById(R.id.oldPrintEditText);
        dataTable = findViewById(R.id.data_table_a);

        header = new DataTableHeader.Builder()
                .item("Invoice No",5)
                .item("Customer Name", 8)
                .item("Date",5)
                .item("Time",5)
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
                    DataTableRow row = new DataTableRow.Builder()
                            .value(String.valueOf(invoice+1))
                            .value(String.valueOf(myDataSnapshot.child("custName").getValue()))
                            .value(datePatternFormats.format(myDataSnapshot.child("date").getValue()))
                            .value(timePatternFormats.format(myDataSnapshot.child("date").getValue()))
                            .value(String.valueOf(myDataSnapshot.child("userId").getValue()))
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
//        fStore.collection("users").document(userID)
//                .get().addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
////                            customerName = document.getString("fName");
//                            String email = document.getString("email");
//                            String num = document.getString("phone");
////                            loc = document.getString("location");
//
//
//                        }
//                    }else {
//
//                    }
//                });

        reviewInvoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = invoiceNumber.getText().toString();
                Intent i = new Intent(AdminMainActivity.this,Menu_Activity.class);
                Intent intent = new Intent(getApplicationContext(),Menu_Activity.class);
                intent.putExtra("inv",s);
                startActivity(intent);
//                startActivity(i);
            }
        });
    }

}
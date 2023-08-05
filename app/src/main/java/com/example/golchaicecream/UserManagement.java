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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class UserManagement extends AppCompatActivity {

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();


    DataTable dataTable;
    DataTableHeader header;
    ProgressBar progressBar ;

    Button btnUserDetails;
    ArrayList<DataTableRow> rows = new ArrayList<>();
    EditText etSrNo;
    int invoice;
    ArrayList<String> arrayList = new ArrayList<>();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        this.progressBar = findViewById(R.id.progressq);
        progressBar.setVisibility(View.VISIBLE);

        dataTable = findViewById(R.id.data_table_as);
        etSrNo = findViewById(R.id.etSrNo);
        btnUserDetails = findViewById(R.id.btnManagement);


        header = new DataTableHeader.Builder()
                .item("Sr No",2)
                .item("Name", 4)
                .item("Location",3)
                .item("Commission",3)
                .build();
        loadTable();
        listeners();

    }

    private void loadTable() {

        fStore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    invoice = 1;
                    for (QueryDocumentSnapshot document : task.getResult()){
                        arrayList.add(document.getId());
                        DataTableRow row = new DataTableRow.Builder()
                                .value(String.valueOf(invoice))
                                .value(document.getString("fName"))
                                .value(document.getString("location"))
                                .value(document.getString("commission"))
                                        .build();

                        rows.add(row);
                        invoice++;
                    }
                    dataTable.setHeader(header);
                    dataTable.setRows(rows);
                    dataTable.inflate(UserManagement.this);
                    progressBar.setVisibility(View.GONE);


                }
            }
        });










    }

    private void listeners() {


        btnUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etSrNo.getText().toString();

                if(s.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                                    "Please enter Sr. No.",
                                    Toast.LENGTH_SHORT)
                            .show();
                }else {
                    if(Integer.parseInt(s)<=invoice-1 && Integer.parseInt(s)>= 1){
                        Intent intent = new Intent(getApplicationContext(),UserDetailsUpdate.class);
                        intent.putExtra("SrNo",s);
                        intent.putExtra("UserID",arrayList.get(Integer.parseInt(s)-1));
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"Enter Valid Sr No.",Toast.LENGTH_SHORT).show();
                    }


                }




            }

        });
    }







}
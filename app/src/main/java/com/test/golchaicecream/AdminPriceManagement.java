package com.test.golchaicecream;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class AdminPriceManagement extends AppCompatActivity {

    DocumentReference fStore = FirebaseFirestore.getInstance().collection("itemDetails").document("AgencyPrices");
    DataTable dataTable;
    DataTableHeader header;
    ProgressBar progressBar ;

    Button priceChange;
    ArrayList<DataTableRow> rows = new ArrayList<>();
    EditText etSrNo;
    int invoice;
    String str[] ={"Kaccha Aaam","Litchi","Strawberry","Coca","PineApple","Orange Bar","Mango Bar","Cup-S","Cup-B","ChocoBar-S","ChocoBar-B","Matka","King Cone-S","King Cone-B","Keshar Pista","Bonanza","Family Pack","Nutty Crunch","Family Pack(2 in 1)"};



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_price_management);
        this.progressBar = findViewById(R.id.progressw);
        progressBar.setVisibility(View.VISIBLE);

        dataTable = findViewById(R.id.data_table_a);
        etSrNo = findViewById(R.id.etSrNo);
        priceChange = findViewById(R.id.priceChange);


        header = new DataTableHeader.Builder()
                .item("Sr No",2)
                .item("Item Names", 5)
                .item("Price",3)
                .build();
        loadTable();
        listeners();

    }

    private void loadTable() {

        fStore.get().addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()){
                    for (int i = 0;i<19;i++){
                        DataTableRow row = new DataTableRow.Builder()
                                .value(String.valueOf(i+1))
                                .value(str[i])
                                .value(documentSnapshot.getString(String.valueOf(i+1)))
                                .build();
                        rows.add(row);

                    }
                    dataTable.setHeader(header);
                    dataTable.setRows(rows);
                    dataTable.inflate(AdminPriceManagement.this);
                    progressBar.setVisibility(View.GONE);

                }else {}
            }else{}

        });

    }

    private void listeners() {


        priceChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etSrNo.getText().toString();

                if(s.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                                    "Please enter Sr. N0.",
                                    Toast.LENGTH_SHORT)
                            .show();
                }else {

                    if(Integer.parseInt(s)<=str.length && Integer.parseInt(s)>= 1){
                        Intent intent = new Intent(getApplicationContext(),ItemDetailsUpdate.class);
                        intent.putExtra("SrNo",s);
                        intent.putExtra("ItemName",str[Integer.parseInt(s)-1]);
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
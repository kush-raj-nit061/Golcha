package com.example.golchaicecream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ItemDetailsUpdateVendor extends AppCompatActivity {

    String ItemName;
    String SrNo;
    String Price;
    EditText itemName,itemPrice;
    Button btnUpdate;

    FirebaseFirestore fsStore = FirebaseFirestore.getInstance();

    DocumentReference fStore = FirebaseFirestore.getInstance().collection("itemDetails").document("VendorPrices");



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_update_vendor);

        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        btnUpdate = findViewById(R.id.btnUpdates);


        Intent i = getIntent();

        ItemName = i.getStringExtra("ItemName");
        SrNo = i.getStringExtra("SrNo");
        itemName.setText(ItemName);

        fStore.get().addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()){
                    Price = documentSnapshot.getString(SrNo);
                    itemPrice.setText(Price);
                }else {}
            }else{}



        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> user =new HashMap<>();

                String pr = itemPrice.getText().toString();
                user.put(SrNo,pr);

                fsStore.collection("itemDetails").document("VendorPrices").update(user)


                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent in = new Intent(ItemDetailsUpdateVendor.this,AdminPriceManagementVendor.class);
                                Toast.makeText(getApplicationContext(),"Price Updated", Toast.LENGTH_SHORT).show();
                                startActivity(in);
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });





            }
        });








    }
}
package com.example.golchaicecream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class UserDetailsUpdate extends AppCompatActivity {
    String srNo,userID;
    FirebaseFirestore fsStore = FirebaseFirestore.getInstance();
    DocumentReference fStore;

    EditText etName,etAddress,etPhone,etComm;
    Button btnUpdate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_update);
        etName = findViewById(R.id.userName);
        etAddress = findViewById(R.id.userAddress);
        etPhone = findViewById(R.id.userPhone);
        etComm = findViewById(R.id.userComm);
        btnUpdate = findViewById(R.id.btnUpdateUserDetails);


        Intent i = getIntent();
        srNo=  i.getStringExtra("SrNo");
        userID=  i.getStringExtra("UserID");


        fStore = FirebaseFirestore.getInstance().collection("users").document(userID);

        fStore.get().addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()){
                    etName.setText(documentSnapshot.getString("fName"));
                    etAddress.setText(documentSnapshot.getString("location"));
                    etPhone.setText(documentSnapshot.getString("phone"));
                    etComm.setText(documentSnapshot.getString("commission"));
                }else {}
            }else{}



        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> user =new HashMap<>();


                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String phone = etPhone.getText().toString();
                String comm = etComm.getText().toString();
                user.put("fName",name);
                user.put("commission",comm);
                user.put("location",address);
                user.put("phone",phone);

                if (TextUtils.isEmpty(comm)) {
                    Toast.makeText(getApplicationContext(),
                                    "Please add Commission",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }


                fsStore.collection("users").document(userID).update(user)


                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent in = new Intent(UserDetailsUpdate.this,UserManagement.class);
                                Toast.makeText(getApplicationContext(),"User Updated", Toast.LENGTH_SHORT).show();
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
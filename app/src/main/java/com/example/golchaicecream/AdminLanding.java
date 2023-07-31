package com.example.golchaicecream;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.sshadkany.neo;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLanding extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button signOut;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing);

        final neo mybtn = findViewById(R.id.rec_text_button);
        signOut = findViewById(R.id.btnSignout);
        final neo manageItems = findViewById(R.id.manageItems);

        manageItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminLanding.this,AdminPriceManagement.class);
                startActivity(i);
            }
        });


        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminLanding.this,AdminMainActivity.class);
                startActivity(i);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                signOutUser();

            }
        });


    }

    private void signOutUser() {

        Intent intent = new Intent(AdminLanding.this,Registration_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }




}
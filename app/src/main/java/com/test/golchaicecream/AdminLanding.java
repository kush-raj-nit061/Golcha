package com.test.golchaicecream;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.test.golchaicecream.R;
import com.github.sshadkany.neo;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLanding extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button signOut;
    ProgressBar progressBar ;
    AlertDialog.Builder builder;


    @SuppressLint({"MissingInflatedId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing);

        RelativeLayout relativeLayout = findViewById(R.id.scrolls);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        try {
            animationDrawable.start();
        }catch (Exception e){
            relativeLayout.setBackground(getResources().getDrawable(R.drawable.background_2));
        }

        this.progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);


        final neo mybtn = findViewById(R.id.rec_text_button);
        signOut = findViewById(R.id.btnSignout);
        final neo manageItems = findViewById(R.id.manageItems);
        final neo manageUsers = findViewById(R.id.manageUsers);

        manageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent i = new Intent(AdminLanding.this,UserManagement.class);
                startActivity(i);
                progressBar.setVisibility(View.GONE);


            }
        });


        manageItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                Intent i = new Intent(AdminLanding.this,AdminPriceManagementRedirect.class);
                startActivity(i);
                progressBar.setVisibility(View.GONE);
            }
        });


        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                Intent i = new Intent(AdminLanding.this,AdminMainActivity.class);
                startActivity(i);
                progressBar.setVisibility(View.GONE);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder= new AlertDialog.Builder(AdminLanding.this);
                builder.setTitle("Sign Out").setMessage("Do you really want to Log Out from App?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressBar.setVisibility(View.VISIBLE);
                                mAuth.signOut();
                                signOutUser();
                                Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();




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
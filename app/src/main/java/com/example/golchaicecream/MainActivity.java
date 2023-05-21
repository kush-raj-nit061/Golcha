package com.example.golchaicecream;

import androidx.appcompat.app.AppCompatActivity;
import com.example.golchaicecream.Registration_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore fStore ;
    FirebaseAuth fAuth;
    String userID;

    Button b2,b1,b3;
    RelativeLayout a4,a1,a2,a3,a5;
    TextView textView;
    TextView tvEmail;
    private MeowBottomNavigation bottomNavigation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        b2=findViewById(R.id.vendor);
        b1=findViewById(R.id.agency);
        b3=findViewById(R.id.pdf);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        a4 = findViewById(R.id.a4);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a5 = findViewById(R.id.a5);
        textView = findViewById(R.id.tvPlaceName);
        tvEmail = findViewById(R.id.tvEmail);


        a5.setVisibility(View.VISIBLE);
        a4.setVisibility(View.GONE);
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);

        fStore.collection("users").document(userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name = document.getString("fName");
                            String email = document.getString("email");

                            tvEmail.setText(email);

                            textView.setText(name);
                        }
                    } else {
                        // Handle the error
                        Exception exception = task.getException();
                        // Log or display an error message
                    }
                });

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.book_order));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_history_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.baseline_person_outline_24));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                switch (model.getId()) {
                    case 1:
                        a1.setVisibility(View.VISIBLE);
                        a4.setVisibility(View.GONE);
                        a2.setVisibility(View.GONE);
                        a3.setVisibility(View.GONE);
                        a5.setVisibility(View.GONE);

                        break;

                    case 2:
                        a2.setVisibility(View.VISIBLE);
                        a1.setVisibility(View.GONE);
                        a4.setVisibility(View.GONE);
                        a3.setVisibility(View.GONE);
                        a5.setVisibility(View.GONE);

                        break;

                    case 3:
                        a3.setVisibility(View.VISIBLE);
                        a2.setVisibility(View.GONE);
                        a4.setVisibility(View.GONE);
                        a1.setVisibility(View.GONE);
                        a5.setVisibility(View.GONE);

                        break;

                    case 4:

                        a4.setVisibility(View.VISIBLE);
                        a3.setVisibility(View.GONE);
                        a2.setVisibility(View.GONE);
                        a1.setVisibility(View.GONE);
                        a5.setVisibility(View.GONE);

                        break;

                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()){
                    case 1:
                        a1.setVisibility(View.VISIBLE);
                        a4.setVisibility(View.GONE);
                        a2.setVisibility(View.GONE);
                        a3.setVisibility(View.GONE);
                        a5.setVisibility(View.GONE);

                        break;

                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()){
                    case 2:
                        a2.setVisibility(View.VISIBLE);
                        a1.setVisibility(View.GONE);
                        a4.setVisibility(View.GONE);
                        a3.setVisibility(View.GONE);
                        a5.setVisibility(View.GONE);

                        break;

                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()){
                    case 3:
                        a3.setVisibility(View.VISIBLE);
                        a2.setVisibility(View.GONE);
                        a4.setVisibility(View.GONE);
                        a1.setVisibility(View.GONE);
                        a5.setVisibility(View.GONE);

                        break;

                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()){
                    case 4:
                        a4.setVisibility(View.VISIBLE);
                        a3.setVisibility(View.GONE);
                        a2.setVisibility(View.GONE);
                        a1.setVisibility(View.GONE);
                        a5.setVisibility(View.GONE);

                        break;

                }
                return null;
            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Menu_Activity.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Vendor_Activity.class);
                startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,PDF_Activity.class);
                startActivity(i);
            }
        });
    }
}
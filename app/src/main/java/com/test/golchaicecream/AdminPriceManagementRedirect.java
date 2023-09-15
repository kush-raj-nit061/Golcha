package com.test.golchaicecream;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPriceManagementRedirect extends AppCompatActivity {
    Button btnAgency,btnVendor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_price_management_redirect);
        btnAgency = findViewById(R.id.btnAgency);
        btnVendor = findViewById(R.id.btnVendor);

        btnAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminPriceManagementRedirect.this,AdminPriceManagement.class);
                startActivity(i);
            }
        });

        btnVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminPriceManagementRedirect.this,AdminPriceManagementVendor.class);
                startActivity(i);
            }
        });



    }
}
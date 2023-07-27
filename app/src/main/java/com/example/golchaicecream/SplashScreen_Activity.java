package com.example.golchaicecream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen_Activity extends AppCompatActivity {
    TextView tv1;
    ImageView iv1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        tv1 = findViewById(R.id.tvSplash);
        iv1 = findViewById(R.id.imageSplash);

        // Delay for 3 seconds before launching the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen_Activity.this, Registration_Activity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 1500);
    }

}

package com.example.golchaicecream;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    TextView tvForgot;
    ProgressBar progressbar;

    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        Btn = findViewById(R.id.login);
        tvForgot = findViewById(R.id.tvForgot);



        progressbar = findViewById(R.id.progressr);
        progressbar.setVisibility(View.GONE);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login_Activity.this, ForgotActivity.class);
                startActivity(i);
            }
        });
    }





    private void loginUserAccount()
    {


        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {

                                    progressbar.setVisibility(View.GONE);

                                    String uid = mAuth.getCurrentUser().getUid();
                                    Map<String,Object> user =new HashMap<>();
                                    user.put("password",password);

                                    fStore.collection("users").document(uid).update(user);


                                    fStore.collection("users").document(uid)
                                            .get().addOnCompleteListener(tasks -> {
                                                if (tasks.isSuccessful()) {
                                                    DocumentSnapshot document = tasks.getResult();
                                                    if (document.exists()) {
                                                        String ut = document.getString("userType");
                                                        assert ut != null;
                                                        if(ut.equals("1")){
                                                            Intent intent
                                                                    = new Intent(Login_Activity.this,
                                                                    AdminLanding.class);
                                                            startActivity(intent);
                                                            finish();



                                                        }else{
                                                            Intent intent
                                                                    = new Intent(Login_Activity.this,
                                                                    MainActivity.class);
                                                            startActivity(intent);
                                                            finish();

                                                        }



                                                    }
                                                }else {

                                                }
                                            });

                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressbar.setVisibility(View.GONE);
                                }
                            }
                        });


    }
}
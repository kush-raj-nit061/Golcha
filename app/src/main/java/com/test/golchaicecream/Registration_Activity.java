package com.test.golchaicecream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration_Activity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText emailTextView, passwordTextView,fullname,phone;
    private Button Btn;
    ProgressBar progressBar ;



    private Button b;


    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
   public String userID;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        this.progressBar = findViewById(R.id.progresss);
        progressBar.setVisibility(View.GONE);


        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.btnregister);
        phone = findViewById(R.id.PhoneNum);
        fullname = findViewById(R.id.fullName);




        b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registration_Activity.this,Login_Activity.class);
                startActivity(i);
            }
        });



        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                registerNewUser();
            }
        });
    }

    private void registerNewUser()
    {

        // show the visibility of progress bar to show loading
        // Take the value of two edit texts in Strings
        String email, password,name,call;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        name = fullname.getText().toString();
        call = phone.getText().toString();

        if (TextUtils.isEmpty(call)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter Phone Number!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }else if(phone.getText().toString().trim().length() != 10){
            Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_LONG)
                    .show();
            return;

        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter Your Name",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }


        // Validations for input email and password
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
        progressBar.setVisibility(View.VISIBLE);



        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        if (task.isSuccessful()) {Toast.makeText(getApplicationContext(),"Registration successful!", Toast.LENGTH_LONG).show();
                            userID = mAuth.getCurrentUser().getUid();

                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",name);
                            user.put("email",email);
                            user.put("phone",call);
                            user.put("password",password);
                            user.put("commission","0");
                            user.put("location","");
                            user.put("userType","0");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onSuccess: user Profile is created for "+ userID);
                                    progressBar.setVisibility(View.GONE);

                                }
                            });

                            // hide the progress bar


                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(Registration_Activity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }


    @Override
    protected void onStart() {

        super.onStart();
        if(mAuth.getCurrentUser() != null){
            progressBar.setVisibility(View.VISIBLE);
            fStore.collection("users").document(mAuth.getCurrentUser().getUid())
                    .get().addOnCompleteListener(tasks -> {
                        if (tasks.isSuccessful()) {
                            DocumentSnapshot document = tasks.getResult();
                            if (document.exists()) {
                                String ut = document.getString("userType");
                                assert ut != null;
                                if(ut.equals("1")){
                                    Intent intent
                                            = new Intent(Registration_Activity.this,
                                            AdminLanding.class);
                                    Toast.makeText(getApplicationContext(),"ADMIN 🙏",Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                    finish();



                                }else{
                                    Intent intent
                                            = new Intent(Registration_Activity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        }else {
                            progressBar.setVisibility(View.GONE);

                        }
                    });

        }



    }



}


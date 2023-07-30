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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login_Activity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    //    private TextView tvLogin;
    TextView tvForgot;
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
//        tvLogin.findViewById(R.id.tvLogin);
        Btn = findViewById(R.id.login);
        tvForgot = findViewById(R.id.tvForgot);



        progressbar = findViewById(R.id.progressbar);

        // Set on Click Listener on Sign-in button
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

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);
//
        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // validations for input email and password
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

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
//                                    Toast.makeText(getApplicationContext(),
//                                                    "Login successful!!",
//                                                    Toast.LENGTH_LONG)
//                                            .show();

                                    // hide the progress bar
                                    progressbar.setVisibility(View.GONE);

                                    String uid = mAuth.getCurrentUser().getUid();


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
                                                                    AdminMainActivity.class);
                                                            startActivity(intent);


                                                        }else{
                                                            Intent intent
                                                                    = new Intent(Login_Activity.this,
                                                                    MainActivity.class);
                                                            startActivity(intent);

                                                        }



                                                    }
                                                }else {

                                                }
                                            });





                                    // if sign-in is successful
                                    // intent to home activity

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
package com.example.golchaicecream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.golchaicecream.Registration_Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    private TextView text1;
    ImageView arrow;
    private PDFView pdfview;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID;
    StorageReference storageReference;
    ImageView ivPlace,logOut;
    AlertDialog.Builder builder;

    long invoice;
    ProgressBar progressBar ;



    Button b2,b1,b3;
    ImageButton imgButton;
    RelativeLayout a4,a1,a2,a3,a5;
    TextView textView,location,invoiceNum;
    LinearLayout personalinfo;
    TextView personalinfobtn;
    TextView tvEmail,tvProfileEmail,tvProfileN,tvEditContact,tvNum,tvEmails,tvLocation,tvAbout,tvEditNameCard,tvNameDown;

    private MeowBottomNavigation bottomNavigation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdfview = findViewById(R.id.pdfview);

        this.progressBar = findViewById(R.id.progressy);
        progressBar.setVisibility(View.GONE);


        text1 =findViewById(R.id.text1);
        arrow = findViewById(R.id.arrow);





        userID = fAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("record/Agency/"+fAuth.getCurrentUser().getUid());
        DatabaseReference mRef=database.getReference("url");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Intent in = getIntent();
                text1.setText(in.getStringExtra("url"));
                String url =text1.getText().toString();
                new RetrivePdfStream().execute(url);
                text1.setText("RETRIVE PDF");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Failed To Load", Toast.LENGTH_SHORT).show();

            }
        });





        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                invoice = dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        invoiceNum = findViewById(R.id.invoiceNum);



        b2=findViewById(R.id.vendor);
        b1=findViewById(R.id.agency);
        b3=findViewById(R.id.pdf);
        location=findViewById(R.id.location);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        a4 = findViewById(R.id.a4);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a5 = findViewById(R.id.a5);
        textView = findViewById(R.id.tvPlaceName);
        tvEmail = findViewById(R.id.tvEmail);
        personalinfo = findViewById(R.id.personalinfo);
        personalinfobtn = findViewById(R.id.personalinfobtn);


        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileN = findViewById(R.id.tvProfileNames);
        imgButton = findViewById(R.id.imgButton);
        ivPlace = findViewById(R.id.ivPlace);


        tvEditContact = findViewById(R.id.tvEditContact);
        tvNum = findViewById(R.id.tvNum);
        tvEmails = findViewById(R.id.Email);
        tvLocation = findViewById(R.id.tvLocation);
        tvAbout = findViewById(R.id.tvAbout);
        tvEditNameCard = findViewById(R.id.tvEditNameCard);
        tvNameDown = findViewById(R.id.tvNameDown);
        logOut =findViewById(R.id.logOut);


        a5.setVisibility(View.VISIBLE);
        a4.setVisibility(View.GONE);
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Sign Out").setMessage("Do you really want to Log Out from App?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
                                fAuth.signOut();
                                signOutUser();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();




            }
        });



        personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.VISIBLE);


                personalinfobtn.setTextColor(getResources().getColor(R.color.blue));


            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();

            }
        });





        fStore.collection("users").document(userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name = document.getString("fName");
                            String email = document.getString("email");
                            String num = document.getString("phone");
                            String loc = document.getString("location");


                            tvProfileN.setText(name);
                            tvProfileEmail.setText(email);
                            tvNum.setText(num);
                            tvEmails.setText(email);
                            tvLocation.setText(loc);
                            location.setText(loc);

//                            tvEmail.setText(email);
//                            textView.setText(name);
                            progressBar.setVisibility(View.VISIBLE);
                            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    Picasso.get().load(uri).into(imgButton);

                                    Picasso.get().load(uri).into(ivPlace);
                                    progressBar.setVisibility(View.GONE);



                                }
                            });


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
                final StorageReference pdfRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"recent.pdf");




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
                invoiceNum.setText(String.valueOf(invoice));
                return null;
            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AgencyMenu.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DisplayUserActivity.class);
                startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OldPrintActivity.class);
                startActivity(i);
            }
        });


        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallInt= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallInt,1000);
            }
        });





    }

    private void signOutUser() {

        Intent intent = new Intent(MainActivity.this,Registration_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    class  RetrivePdfStream extends AsyncTask<String,Void, InputStream>{


        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {

                URL url = new URL (strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode()== 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){

                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfview.fromStream(inputStream).load();
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri =data.getData();
//                imgButton.setImageURI(imageUri);
                progressBar.setVisibility(View.VISIBLE);
                
                uploadImageToFirebase(imageUri);
            }
        }


    }

    private void uploadImageToFirebase(Uri imageUri) {

        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile((imageUri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.get().load(uri).into(imgButton);
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Failed.",Toast.LENGTH_LONG).show();
            }
        });


    }
}


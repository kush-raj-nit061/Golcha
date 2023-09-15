package com.test.golchaicecream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.golchaicecream.Room.Users;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity implements Serializable {

    EditText etName,etAddress,etNotes;
    Button btnSave,btnOld;


    EditText[] editTexts = new EditText[19];
    Integer[] s = new Integer[19];
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etAddress = findViewById(R.id.vendorAddress);
        etName = findViewById(R.id.vendorName);
        etNotes = findViewById(R.id.etNotes);
        btnSave = findViewById(R.id.btnSave);
        btnOld = findViewById(R.id.btnOld);
        btnOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this,DisplayUserActivity.class);
                startActivity(i);
            }
        });




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String si = datePatternFormat.format(new Date());
                String q = si.substring(0,11);
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String notes = etNotes.getText().toString();
                editTexts[0] = findViewById(R.id.tvKacchaDep);
                editTexts[1]= findViewById(R.id.tvLitchiDep);
                editTexts[2] = findViewById(R.id.tvStrawDep);
                editTexts[3]= findViewById(R.id.tvColaDep);
                editTexts[4] = findViewById(R.id.tvPineDep);
                editTexts[5] = findViewById(R.id.tvOrangDep);
                editTexts[6]= findViewById(R.id.tvMangoDep);
                editTexts[7]= findViewById(R.id.tvCupsDep);
                editTexts[8] = findViewById(R.id.tvCupbDep);
                editTexts[9]= findViewById(R.id.tvChocosDep);
                editTexts[10]= findViewById(R.id.tvChocobDep);
                editTexts[11]= findViewById(R.id.tvMatkaDep);
                editTexts[12]= findViewById(R.id.tvConesDep);
                editTexts[13]= findViewById(R.id.tvConebDep);
                editTexts[15]= findViewById(R.id.tvPistaDep);
                editTexts[16]= findViewById(R.id.tvBonanzaDep);
                editTexts[17]= findViewById(R.id.tvFamilyDep);
                editTexts[18]= findViewById(R.id.tvFamily2Dep);
                editTexts[14]= findViewById(R.id.tvNuttyDep);
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter Name",
                                    Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(etAddress.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter Address",
                                    Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                for (int i = 0; i < editTexts.length; i++) {
                    String text = editTexts[i].getText().toString().trim();
                    if (text.isEmpty()) {
                        editTexts[i].setText("0");
                        text = "0";
                    }
                    s[i] = Integer.parseInt(editTexts[i].getText().toString().trim());
                }

                Users users = new Users(0,q,0,name,address,"",notes,s[0],s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],s[9],s[10],s[11],s[12],s[13],s[14],s[15],s[16],s[17],s[18]);

                Intent i  = new Intent(MainActivity2.this,DisplayUserActivity.class);
                i.putExtra("user",users);



                etName.setText("");
                etAddress.setText("");
                startActivity(i);



            }
        });

    }


}
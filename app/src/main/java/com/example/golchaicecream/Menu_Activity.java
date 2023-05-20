package com.example.golchaicecream;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Menu_Activity extends AppCompatActivity {
    EditText[] editTexts = new EditText[18];
    Button Calc;
    TextView[] textViews = new TextView[18];
    TextView tvTotal,tvCommision,tvDues,tv,tvT;
    int arr[] = {250,250,250,250,250,250,250,150,240,300,360,300,420,500,240,200,100,400};
    float total;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        generate();
        Calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float result = addEditTextValues(editTexts,textViews);
                float comm = (45 * result) / 100;
                float dues = result-comm;
                tvTotal.setText(String.valueOf(result));
                tvCommision.setText(String.valueOf(comm));
                tvDues.setText(String.valueOf(dues));
            }
        });
    }

    private void generate() {
        editTexts[0] = findViewById(R.id.tvKaccha);
        editTexts[1]= findViewById(R.id.tvLitchi);
        editTexts[2] = findViewById(R.id.tvStraw);
        editTexts[3]= findViewById(R.id.tvCola);
        editTexts[4] = findViewById(R.id.tvPine);
        editTexts[5] = findViewById(R.id.tvOrang);
        editTexts[6]= findViewById(R.id.tvMango);
        editTexts[7]= findViewById(R.id.tvCups);
        editTexts[8] = findViewById(R.id.tvCupb);
        editTexts[9]= findViewById(R.id.tvChocos);
        editTexts[10]= findViewById(R.id.choco);
        editTexts[11]= findViewById(R.id.tvMatka);
        editTexts[12]= findViewById(R.id.tvCones);
        editTexts[13]= findViewById(R.id.tvConeb);
        editTexts[14]= findViewById(R.id.tvPista);
        editTexts[15]= findViewById(R.id.tvBonanza);
        editTexts[16]= findViewById(R.id.tvFamily);
        editTexts[17]= findViewById(R.id.tvNutty);


        textViews[0] = findViewById(R.id.tvTotal1);
        textViews[1]= findViewById(R.id.tvTotal2);
        textViews[2] = findViewById(R.id.tvTotal3);
        textViews[3]= findViewById(R.id.tvTotal4);
        textViews[4] = findViewById(R.id.tvTotal5);
        textViews[5] = findViewById(R.id.tvTotal6);
        textViews[6]= findViewById(R.id.tvTotal7);
        textViews[7]= findViewById(R.id.tvTotal8);
        textViews[8] = findViewById(R.id.tvTotal9);
        textViews[9]= findViewById(R.id.tvTotal10);
        textViews[10]= findViewById(R.id.tvTotal11);
        textViews[11]= findViewById(R.id.tvTotal12);
        textViews[12]= findViewById(R.id.tvTotal13);
        textViews[13]= findViewById(R.id.tvTotal14);
        textViews[14]= findViewById(R.id.tvTotal15);
        textViews[15]= findViewById(R.id.tvTotal16);
        textViews[16]= findViewById(R.id.tvTotal17);
        textViews[17]= findViewById(R.id.tvTotal18);

        tvT =findViewById(R.id.Tot);
        tvTotal = findViewById(R.id.tvTotal);
        tvCommision = findViewById(R.id.tvCommision);
        tv = findViewById(R.id.second_edit_text);
        Calc = findViewById(R.id.btnCT);
        tvDues = findViewById(R.id.tvDues);
    }

    public int addEditTextValues(EditText[] editTexts,TextView[] textView) {
        int sum = 0;
        tvT.setText("Total");
        for (int i = 0; i < editTexts.length; i++) {
            String text = editTexts[i].getText().toString().trim();

            if (!text.isEmpty()) {
                try {
                    int value = Integer.parseInt(text);
                    value *= arr[i];
                    textViews[i].setText(String.valueOf(value));
                    sum += value;
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            }
        }
        return sum;
    }



}
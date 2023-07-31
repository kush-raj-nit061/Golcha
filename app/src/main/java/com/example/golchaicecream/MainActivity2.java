package com.example.golchaicecream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;

    ArrayList<UsersItem> usersItemArrayList;
    UserRecyclerAdapter adapter;

    Button buttonAdd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items");

        recyclerView = findViewById(R.id.itemLists);





        recyclerView.setAdapter(adapter);

        buttonAdd = findViewById(R.id.btnAddItem);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MainActivity2.this);

            }
        });

        readData();
    }

    private void readData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usersItemArrayList = new ArrayList<>();
//                usersItemArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    UsersItem users = dataSnapshot.getValue(UsersItem.class);
                    usersItemArrayList.add(users);
                    Toast.makeText(getApplicationContext(),UsersItem.class.toString(), Toast.LENGTH_SHORT).show();

                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));

                adapter = new UserRecyclerAdapter(MainActivity2.this,usersItemArrayList);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewDialogAdd {
        public void showDialog(Context context){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.update_popup);
            dialog.show();

            EditText itemName = dialog.findViewById(R.id.itemName);
            EditText itemBoxQty = dialog.findViewById(R.id.itemBoxQty);
            EditText itemPrice = dialog.findViewById(R.id.itemPrice);

            Button buttonAdd = dialog.findViewById(R.id.btnUpdate);

            buttonAdd.setText("ADD");

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = "item"+new Date().getTime();
                    String nameItem = itemName.getText().toString();
                    String itemQtyBox = itemBoxQty.getText().toString();
                    String priceItem = itemPrice.getText().toString();


                    if(nameItem.isEmpty() || itemQtyBox.isEmpty() || priceItem.isEmpty()){
                        Toast.makeText(context,"Please Enter All Data...", Toast.LENGTH_LONG).show();
                    }else{
                        databaseReference.child(id).setValue(new UsersItem(nameItem,itemQtyBox,priceItem));
                        Toast.makeText(context,"Done",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });


        }

    }
}
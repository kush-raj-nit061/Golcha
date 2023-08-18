package com.example.golchaicecream;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.golchaicecream.Room.UserDao;
import com.example.golchaicecream.Room.UserDatabase;
import com.example.golchaicecream.Room.Users;
import com.github.sshadkany.CircleButton;
import com.github.sshadkany.neo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import carbon.widget.FloatingActionButton;

public class DisplayUserActivity extends AppCompatActivity implements AdapterListener {

    private UserDatabase userDatabase;
    private UserDao userDao;

    RecyclerView myRecycler;
    private UserAdapter userAdapter;
    AlertDialog.Builder builder;
    SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        userDatabase= UserDatabase.getInstance(this);
        userDao =userDatabase.getDao();

        myRecycler = findViewById(R.id.recycler);
        userAdapter = new UserAdapter(this,this);
        myRecycler.setAdapter(userAdapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));
        Intent i = getIntent();
        Users users = null;
        users =(Users) getIntent().getSerializableExtra("user");

        userAdapter.addUser(users);
        if(users!=null){
            userDao.insert(users);
        }

        CircleButton btnFloating = findViewById(R.id.btnFloating);

        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisplayUserActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });


    }
    private void fetchData(){
        userAdapter.clearData();
        List<Users> usersList = userDao.getAllUsers();
        String si = datePatternFormat.format(new Date());
        String q = si.substring(0,11);
        for (int i = 0;i<usersList.size();i++){
            Users users = usersList.get(i);

            if(!Objects.equals(users.getDate(), q)){

                if(!users.getTick().equals("🛒")){
                    users.setTick("");
                }

            }
            userAdapter.addUser(users);

        }
    }

    @Override
    public void onUpdate(Users users) {
        Intent intent = new Intent(this,Vendor_Activity.class);
        intent.putExtra("model",users);
        startActivity(intent);


    }

    @Override
    public void onDelete(int id, int pos) {
        builder= new AlertDialog.Builder(this);
        builder.setTitle("Delete Vendor data").setMessage("Do you really want to delete vendor data.")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDao.delete(id);
                        userAdapter.removeUser(pos);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();


    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }
}
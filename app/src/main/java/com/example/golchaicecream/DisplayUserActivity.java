package com.example.golchaicecream;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.golchaicecream.Room.UserDao;
import com.example.golchaicecream.Room.UserDatabase;
import com.example.golchaicecream.Room.Users;

import java.util.List;

public class DisplayUserActivity extends AppCompatActivity implements AdapterListener {

    private UserDatabase userDatabase;
    private UserDao userDao;

    RecyclerView myRecycler;
    private UserAdapter userAdapter;

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


    }
    private void fetchData(){
        userAdapter.clearData();
        List<Users> usersList = userDao.getAllUsers();
        for (int i = 0;i<usersList.size();i++){
            Users users = usersList.get(i);
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
        userDao.delete(id);
        userAdapter.removeUser(pos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }
}
package com.example.golchaicecream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.golchaicecream.Room.Users;

import java.util.ArrayList;
import java.util.List;

import carbon.widget.FloatingActionButton;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context context;
    private List<Users> usersList;

    private AdapterListener adapterListener;

    public UserAdapter(Context context, AdapterListener listener){
        this.context = context;
        usersList=new ArrayList<>();
        this.adapterListener=listener;

    }

    public void addUser(Users users){
        usersList.add(users);
        notifyDataSetChanged();
    }
    public void removeUser(int position){
        usersList.remove(position);
        notifyDataSetChanged();
    }
    public void clearData(){
        usersList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Users users = usersList.get(position);
        holder.name.setText(users.getName());
        holder.address.setText(users.getAddress());
        holder.tick.setText(users.getTick());
        holder.date.setText(users.getDate());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.onUpdate(users);

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.onDelete(users.getId(),position);

            }
        });
        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tick.setText("🛒");


            }
        });


    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name,address,kaccha,tick,date;
        private ImageView update,delete;


        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address= itemView.findViewById(R.id.Address);
            update = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            tick = itemView.findViewById(R.id.tick);
            date = itemView.findViewById(R.id.Date);

        }
    }



}

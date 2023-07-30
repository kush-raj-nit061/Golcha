package com.example.golchaicecream;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<UsersItem> usersItemArrayList;
    DatabaseReference databaseReference;

    public UserRecyclerAdapter(Context context, ArrayList<UsersItem> usersItemArrayList) {
        this.context = context;
        this.usersItemArrayList = usersItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  = layoutInflater.inflate(R.layout.items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UsersItem users = usersItemArrayList.get(position);
        holder.itemName.setText(users.getItemName());
        holder.itemBoxQty.setText(users.getItemBoxQty());
        holder.itemPrice.setText(users.getItemPrice());
        ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
        viewDialogUpdate.showDialog(context,users.getItemName(), users.getItemBoxQty(), users.getItemPrice());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemName,itemPrice,itemBoxQty;
        Button btnEdit,btnDelete;
        RelativeLayout rv;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNames);
            itemPrice = itemView.findViewById(R.id.itemPrices);
            itemBoxQty = itemView.findViewById(R.id.itemBoxQtys);
            btnDelete = itemView.findViewById(R.id.btnDeletes);
            btnEdit= itemView.findViewById(R.id.btnEdit);
            rv = itemView.findViewById(R.id.rv);
        }
    }

    public class ViewDialogUpdate {
        public void showDialog(Context context, String Name,String BoxQty,String Price){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.update_popup);

            EditText itemName = dialog.findViewById(R.id.itemName);
            EditText itemBoxQty = dialog.findViewById(R.id.itemBoxQty);
            EditText itemPrice = dialog.findViewById(R.id.itemPrice);

            itemName.setText(Name);
            itemBoxQty.setText(BoxQty);
            itemPrice.setText(Price);

            Button buttonUpdate = dialog.findViewById(R.id.btnUpdate);

            buttonUpdate.setText("UPDATE");

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String newnameItem = itemName.getText().toString();
                    String newitemQtyBox = itemBoxQty.getText().toString();
                    String newpriceItem = itemPrice.getText().toString();


                    if(Name.isEmpty() || BoxQty.isEmpty() || Price.isEmpty()){
                        Toast.makeText(context,"Please Enter All Data...", Toast.LENGTH_LONG).show();
                    }else{

                        if(newnameItem.equals(Name) && newitemQtyBox.equals(BoxQty)&& newpriceItem.equals(Price)){
                            Toast.makeText(context,"You haven't change anything",Toast.LENGTH_LONG).show();

                        }else{
                            databaseReference.child("Items").child(newnameItem).setValue(new UsersItem(Name,BoxQty,Price));
                            Toast.makeText(context,"Item Updated Successfully",Toast.LENGTH_LONG).show();
                            dialog.dismiss();


                        }


                    }
                }
            });


        }

    }

}

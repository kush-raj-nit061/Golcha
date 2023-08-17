package com.example.golchaicecream;

import com.example.golchaicecream.Room.Users;

public interface AdapterListener {
    void onUpdate(Users users);
    void onDelete(int id,int pos);
}

package com.test.golchaicecream;

import com.test.golchaicecream.Room.Users;

public interface AdapterListener {
    void onUpdate(Users users);
    void onDelete(int id,int pos);
}

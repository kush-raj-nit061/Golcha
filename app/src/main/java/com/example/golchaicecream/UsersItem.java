package com.example.golchaicecream;

public class UsersItem {

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemBoxQty() {
        return itemBoxQty;
    }

    public void setItemBoxQty(String itemBoxQty) {
        this.itemBoxQty = itemBoxQty;
    }

    String itemName,itemPrice,itemBoxQty;



    public UsersItem(){}



    public UsersItem(String itemName, String itemPrice, String itemBoxQty) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemBoxQty = itemBoxQty;

    }
}

package com.example.golchaicecream.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class Users implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private  int id;

    public String getTick() {
        return tick;
    }

    public void setTick(String tick) {
        this.tick = tick;
    }

    String tick;
    String name;
    String address;
    String notes;
    int kacchaQtyDepart;
    int litchiQtyDepart;
    int strawQtyDepart;
    int colaQtyDepart;
    int pineQtyDepart;
    int orangeQtyDepart;
    int mangoQtyDepart;
    int cupSQtyDepart;
    int cupBQtyDepart;
    int chocoSQtyDepart;
    int chocoBQtyDepart;
    int matkaQtyDepart;
    int coneSQtyDepart;
    int coneBQtyDepart;
    int nuttyQtyDepart;
    int keshaerQtyDepart;
    int bonanzaQtyDepart;
    int familyQtyDepart;
    int family2QtyDepart;
    float commision;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getCommision() {
        return commision;
    }

    public void setCommision(float commision) {
        this.commision = commision;
    }

    public Users(int id,String date, float comm, String name, String address, String tick, String notes, int kacchaQtyDepart, int litchiQtyDepart, int strawQtyDepart, int colaQtyDepart, int pineQtyDepart, int orangeQtyDepart, int mangoQtyDepart, int cupSQtyDepart, int cupBQtyDepart, int chocoSQtyDepart, int chocoBQtyDepart, int matkaQtyDepart, int coneSQtyDepart, int coneBQtyDepart, int nuttyQtyDepart, int keshaerQtyDepart, int bonanzaQtyDepart, int familyQtyDepart, int family2QtyDepart) {
        this.date=date;
        this.commision=comm;
        this.id = id;
        this.name = name;
        this.address = address;
        this.notes = notes;
        this.tick = tick;
        this.kacchaQtyDepart = kacchaQtyDepart;
        this.litchiQtyDepart = litchiQtyDepart;
        this.strawQtyDepart = strawQtyDepart;
        this.colaQtyDepart = colaQtyDepart;
        this.pineQtyDepart = pineQtyDepart;
        this.orangeQtyDepart = orangeQtyDepart;
        this.mangoQtyDepart = mangoQtyDepart;
        this.cupSQtyDepart = cupSQtyDepart;
        this.cupBQtyDepart = cupBQtyDepart;
        this.chocoSQtyDepart = chocoSQtyDepart;
        this.chocoBQtyDepart = chocoBQtyDepart;
        this.matkaQtyDepart = matkaQtyDepart;
        this.coneSQtyDepart = coneSQtyDepart;
        this.coneBQtyDepart = coneBQtyDepart;
        this.nuttyQtyDepart = nuttyQtyDepart;
        this.keshaerQtyDepart = keshaerQtyDepart;
        this.bonanzaQtyDepart = bonanzaQtyDepart;
        this.familyQtyDepart = familyQtyDepart;
        this.family2QtyDepart = family2QtyDepart;
    }

    public Users(){}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getKacchaQtyDepart() {
        return kacchaQtyDepart;
    }

    public void setKacchaQtyDepart(int kacchaQtyDepart) {
        this.kacchaQtyDepart = kacchaQtyDepart;
    }

    public int getLitchiQtyDepart() {
        return litchiQtyDepart;
    }

    public void setLitchiQtyDepart(int litchiQtyDepart) {
        this.litchiQtyDepart = litchiQtyDepart;
    }

    public int getStrawQtyDepart() {
        return strawQtyDepart;
    }

    public void setStrawQtyDepart(int strawQtyDepart) {
        this.strawQtyDepart = strawQtyDepart;
    }

    public int getColaQtyDepart() {
        return colaQtyDepart;
    }

    public void setColaQtyDepart(int colaQtyDepart) {
        this.colaQtyDepart = colaQtyDepart;
    }

    public int getPineQtyDepart() {
        return pineQtyDepart;
    }

    public void setPineQtyDepart(int pineQtyDepart) {
        this.pineQtyDepart = pineQtyDepart;
    }

    public int getOrangeQtyDepart() {
        return orangeQtyDepart;
    }

    public void setOrangeQtyDepart(int orangeQtyDepart) {
        this.orangeQtyDepart = orangeQtyDepart;
    }

    public int getMangoQtyDepart() {
        return mangoQtyDepart;
    }

    public void setMangoQtyDepart(int mangoQtyDepart) {
        this.mangoQtyDepart = mangoQtyDepart;
    }

    public int getCupSQtyDepart() {
        return cupSQtyDepart;
    }

    public void setCupSQtyDepart(int cupSQtyDepart) {
        this.cupSQtyDepart = cupSQtyDepart;
    }

    public int getCupBQtyDepart() {
        return cupBQtyDepart;
    }

    public void setCupBQtyDepart(int cupBQtyDepart) {
        this.cupBQtyDepart = cupBQtyDepart;
    }

    public int getChocoSQtyDepart() {
        return chocoSQtyDepart;
    }

    public void setChocoSQtyDepart(int chocoSQtyDepart) {
        this.chocoSQtyDepart = chocoSQtyDepart;
    }

    public int getChocoBQtyDepart() {
        return chocoBQtyDepart;
    }

    public void setChocoBQtyDepart(int chocoBQtyDepart) {
        this.chocoBQtyDepart = chocoBQtyDepart;
    }

    public int getMatkaQtyDepart() {
        return matkaQtyDepart;
    }

    public void setMatkaQtyDepart(int matkaQtyDepart) {
        this.matkaQtyDepart = matkaQtyDepart;
    }

    public int getConeSQtyDepart() {
        return coneSQtyDepart;
    }

    public void setConeSQtyDepart(int coneSQtyDepart) {
        this.coneSQtyDepart = coneSQtyDepart;
    }

    public int getConeBQtyDepart() {
        return coneBQtyDepart;
    }

    public void setConeBQtyDepart(int coneBQtyDepart) {
        this.coneBQtyDepart = coneBQtyDepart;
    }

    public int getNuttyQtyDepart() {
        return nuttyQtyDepart;
    }

    public void setNuttyQtyDepart(int nuttyQtyDepart) {
        this.nuttyQtyDepart = nuttyQtyDepart;
    }

    public int getKeshaerQtyDepart() {
        return keshaerQtyDepart;
    }

    public void setKeshaerQtyDepart(int keshaerQtyDepart) {
        this.keshaerQtyDepart = keshaerQtyDepart;
    }

    public int getBonanzaQtyDepart() {
        return bonanzaQtyDepart;
    }

    public void setBonanzaQtyDepart(int bonanzaQtyDepart) {
        this.bonanzaQtyDepart = bonanzaQtyDepart;
    }

    public int getFamilyQtyDepart() {
        return familyQtyDepart;
    }

    public void setFamilyQtyDepart(int familyQtyDepart) {
        this.familyQtyDepart = familyQtyDepart;
    }

    public int getFamily2QtyDepart() {
        return family2QtyDepart;
    }

    public void setFamily2QtyDepart(int family2QtyDepart) {
        this.family2QtyDepart = family2QtyDepart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}

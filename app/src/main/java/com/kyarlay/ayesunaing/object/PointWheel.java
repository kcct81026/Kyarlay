package com.kyarlay.ayesunaing.object;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class PointWheel extends UniversalPost {

    @SerializedName("id")
    private int id;


    @SerializedName("title")
    private String title;


    @SerializedName("amount")
    private int amount;

    @SerializedName("photo")
    private String photo;

    @SerializedName("dimen")
    private int dimen;

    @SerializedName("selected")
    private int selected;

    public PointWheel(String title, int amount, String photo, int dimen, int selected) {
        this.title = title;
        this.amount = amount;
        this.photo = photo;
        this.dimen = dimen;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}

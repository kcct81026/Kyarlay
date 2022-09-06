package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SafeMainObj extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("photo_url")
    private String photo_url;

    @SerializedName("photo_dimen")
    private int photo_dimen;

    @SerializedName("safe_items")
    private List<SafeItem> safeItemList;

    public SafeMainObj(){}


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

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public int getPhoto_dimen() {
        return photo_dimen;
    }

    public void setPhoto_dimen(int photo_dimen) {
        this.photo_dimen = photo_dimen;
    }

    public List<SafeItem> getSafeItemList() {
        return safeItemList;
    }

    public void setSafeItemList(List<SafeItem> safeItemList) {
        this.safeItemList = safeItemList;
    }
}

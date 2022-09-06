package com.kyarlay.ayesunaing.object;

import android.content.pm.PackageInfo;

import com.google.gson.annotations.SerializedName;

public class PackageObject extends UniversalPost {

    @SerializedName("package_id")
    private int package_id;

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String desc;

    @SerializedName("price")
    private int price;

    @SerializedName("photo_url")
    private String photo_url;

    @SerializedName("photo_dimen")
    private int photo_dimen;

    public PackageObject(){}

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
}

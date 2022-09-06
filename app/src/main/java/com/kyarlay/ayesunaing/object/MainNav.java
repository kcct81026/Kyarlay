package com.kyarlay.ayesunaing.object;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class MainNav extends UniversalPost {

    @SerializedName("type")
    private String type;

    @SerializedName("title")
    private String title;

    @SerializedName("img_url")
    private String img_url;

    public MainNav(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}


/*
[{"type":"flashsale","title":"Flash Sale","img_url":"https://res.cloudinary.com/tech-myanmar/image/upload/v1605115185/kyarlay/icons/flashsale.jpg"},{"type":"promotions","title":"Promotion","img_url":"https://res.cloudinary.com/tech-myanmar/image/upload/v1605115185/kyarlay/icons/promotion.jpg"},{"type":"brands","title":"Brands","img_url":"https://res.cloudinary.com/tech-myanmar/image/upload/v1605115185/kyarlay/icons/brands.jpg"},
{"type":"new_arrival","title":"New Arrival","img_url":"https://res.cloudinary.com/tech-myanmar/image/upload/v1605115185/kyarlay/icons/new.jpg"}]
 */
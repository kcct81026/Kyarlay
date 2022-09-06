package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class PostCategory extends UniversalPost {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("tag")
    private String tag;

    @SerializedName("photo")
    private String photo;

    public PostCategory(){}


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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

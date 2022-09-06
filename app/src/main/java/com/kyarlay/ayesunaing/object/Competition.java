package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class Competition extends UniversalPost{

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("desc")
    private String desc;

    @SerializedName("start_at")
    private String start_at;

    @SerializedName("end_at")
    private String end_at;

    @SerializedName("img_url")
    private String img_url;

    @SerializedName("img_dimen")
    private int img_dimen;

    public Competition(){}

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getImg_dimen() {
        return img_dimen;
    }

    public void setImg_dimen(int img_dimen) {
        this.img_dimen = img_dimen;
    }
}

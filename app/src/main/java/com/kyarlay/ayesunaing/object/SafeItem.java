package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class SafeItem extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("short_title")
    private String short_title;

    @SerializedName("long_title")
    private String long_title;

    @SerializedName("desc")
    private String desc;

    @SerializedName("safe_status")
    private String safe_status;

    public SafeItem(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getLong_title() {
        return long_title;
    }

    public void setLong_title(String long_title) {
        this.long_title = long_title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSafe_status() {
        return safe_status;
    }

    public void setSafe_status(String safe_status) {
        this.safe_status = safe_status;
    }
}

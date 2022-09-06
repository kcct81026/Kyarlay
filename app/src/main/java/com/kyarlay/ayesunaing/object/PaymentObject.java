package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class PaymentObject extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("img_url")
    private String img_url;

    @SerializedName("commission")
    private float commission;

    @SerializedName("min")
    private int min;

    @SerializedName("max")
    private int max;

    @SerializedName("tag")
    private String tag;

    @SerializedName("acc_name")
    private String acc_name;

    @SerializedName("acc_num")
    private String acc_num;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public String getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(String acc_num) {
        this.acc_num = acc_num;
    }
}

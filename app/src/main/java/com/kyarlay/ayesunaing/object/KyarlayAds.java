package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class KyarlayAds extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("image_dimen")
    private int image_dimen;


    @SerializedName("type")
    private String type;


    @SerializedName("target_url")
    private String target_url;


    public KyarlayAds(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getImage_dimen() {
        return image_dimen;
    }

    public void setImage_dimen(int image_dimen) {
        this.image_dimen = image_dimen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }
}

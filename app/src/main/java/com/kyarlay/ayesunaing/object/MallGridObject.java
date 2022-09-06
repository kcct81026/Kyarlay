package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class MallGridObject extends UniversalPost{
    @SerializedName("img_url")
    private String url;
    @SerializedName("type")
    private String type;
    @SerializedName("title")
    private String title;
    @SerializedName("img_dimen")
    private int dimen;

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}



package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class PointInfo extends UniversalPost {

    @SerializedName("type")
    private String type;

    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    @SerializedName("body")
    private String body;

    @SerializedName("data")
    private String data;

    @SerializedName("status")
    private String status;

    @SerializedName("cover_photo")
    private String cover_photo;

    @SerializedName("cover_photo_dimen")
    private int cover_photo_dimen;

    public PointInfo(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public int getCover_photo_dimen() {
        return cover_photo_dimen;
    }

    public void setCover_photo_dimen(int cover_photo_dimen) {
        this.cover_photo_dimen = cover_photo_dimen;
    }
}

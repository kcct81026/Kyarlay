package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class Clicnic extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String desc;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    String address;

    @SerializedName("clinic_type")
    private String clinic_type;

    @SerializedName("township")
    private String township;

    @SerializedName("logo_url")
    private String logo_url;

    @SerializedName("preview_photo_url")
    private String preview_photo_url;

    @SerializedName("preview_photo_dimen")
    private int preview_photo_dimen;

    public Clicnic(){}


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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClinic_type() {
        return clinic_type;
    }

    public void setClinic_type(String clinic_type) {
        this.clinic_type = clinic_type;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getPreview_photo_url() {
        return preview_photo_url;
    }

    public void setPreview_photo_url(String preview_photo_url) {
        this.preview_photo_url = preview_photo_url;
    }

    public int getPreview_photo_dimen() {
        return preview_photo_dimen;
    }

    public void setPreview_photo_dimen(int preview_photo_dimen) {
        this.preview_photo_dimen = preview_photo_dimen;
    }
}

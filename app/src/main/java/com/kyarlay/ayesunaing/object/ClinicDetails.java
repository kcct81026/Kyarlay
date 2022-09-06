package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ClinicDetails extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String desc;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("clinic_type")
    private String clinic_type;

    @SerializedName("township")
    private String township;

    @SerializedName("logo_url")
    private String logo_url;

    @SerializedName("photos")
    private List<Images> images = new ArrayList<>();

    @SerializedName("packages")
    private List<PackageObject> packageObjectList = new ArrayList<>();

    public ClinicDetails(){}

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

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<PackageObject> getPackageObjectList() {
        return packageObjectList;
    }

    public void setPackageObjectList(List<PackageObject> packageObjectList) {
        this.packageObjectList = packageObjectList;
    }
}

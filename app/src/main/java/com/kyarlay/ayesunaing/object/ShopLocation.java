package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShopLocation extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("location_type")
    private String location_type;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lng")
    private String lng;

    @SerializedName("photos")
    private List<Images> photos = new ArrayList<>();

    @SerializedName("township_name")
    private String township_name;


    public ShopLocation(){}

    public ShopLocation(int id, String name, String location_type, String phone, String address, String lat, String lng, List<Images> photos, String township_name) {
        this.id = id;
        this.name = name;
        this.location_type = location_type;
        this.phone = phone;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.photos = photos;
        this.township_name = township_name;
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

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public List<Images> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Images> photos) {
        this.photos = photos;
    }

    public String getTownship_name() {
        return township_name;
    }

    public void setTownship_name(String township_name) {
        this.township_name = township_name;
    }
}

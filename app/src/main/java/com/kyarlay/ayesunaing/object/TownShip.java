package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class TownShip extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("store_location_id")
    private int store_location_id;

    @SerializedName("delivery_type")
    private int delivery_type;

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
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

    public int getStore_location_id() {
        return store_location_id;
    }

    public void setStore_location_id(int store_location_id) {
        this.store_location_id = store_location_id;
    }
}
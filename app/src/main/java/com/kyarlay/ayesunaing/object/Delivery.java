package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesunaing on 9/11/16.
 */
public class Delivery {
    /*
    	"": 3,
	"delivery_type": 0,
	"price": 1000,
	"express_delivery_days": "1 ~ 3 days",
	"normal_price": 0,
	"normal_delivery_days": "4 ~ 7 days",
	"title": "နယ်",
	"desc": "ရန်ကုန်ရှိ အဝေးပြေးကားဂိတ်အထိပို့ပေးပါတယ်။ အဝေးပြေးကားတန်ဆာခကို ဝယ်သူဘက်မှပေးရပါမယ်။",
	"free_delivery_amount": 0,
	"first_time_free": 0,
	"choose_timing":
     */

    int pid;

    @SerializedName("delivery_type")
    int delivery_type;

    @SerializedName("express_delivery_days")
    String express_delivery_days;

    @SerializedName("normal_price")
    int normal_price;

    @SerializedName("normal_delivery_days")
    String normal_delivery_days;


    @SerializedName("id")
    int id;

    @SerializedName("title")
    String name;

    @SerializedName("desc")
    String desc;

    @SerializedName("price")
    int price;

    @SerializedName("free_delivery_amount")
    int freeDelivery;

    @SerializedName("choose_timing")
    int choose_timing;

    @SerializedName("first_time_free")
    int first_time_free;

    public Delivery(){}

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getExpress_delivery_days() {
        return express_delivery_days;
    }

    public void setExpress_delivery_days(String express_delivery_days) {
        this.express_delivery_days = express_delivery_days;
    }

    public int getNormal_price() {
        return normal_price;
    }

    public void setNormal_price(int normal_price) {
        this.normal_price = normal_price;
    }

    public String getNormal_delivery_days() {
        return normal_delivery_days;
    }

    public void setNormal_delivery_days(String normal_delivery_days) {
        this.normal_delivery_days = normal_delivery_days;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(int freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public int getChoose_timing() {
        return choose_timing;
    }

    public void setChoose_timing(int choose_timing) {
        this.choose_timing = choose_timing;
    }

    public int getFirst_time_free() {
        return first_time_free;
    }

    public void setFirst_time_free(int first_time_free) {
        this.first_time_free = first_time_free;
    }
}

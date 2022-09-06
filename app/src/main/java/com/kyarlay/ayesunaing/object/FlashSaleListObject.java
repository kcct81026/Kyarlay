package com.kyarlay.ayesunaing.object;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class FlashSaleListObject extends UniversalPost{

    @SerializedName("id")
    private int id;

    @SerializedName("start_date")
    private String start_date;

    @SerializedName("end_date")
    private String end_date;

    @SerializedName("available_quantity")
    private int available_quantity;

    @SerializedName("reserved_quantity")
    private int reserved_quantity;

    @SerializedName("discount")
    private int discount;

    @SerializedName("product")
    private Product product;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public int getReserved_quantity() {
        return reserved_quantity;
    }

    public void setReserved_quantity(int reserved_quantity) {
        this.reserved_quantity = reserved_quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

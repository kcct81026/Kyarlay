package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Flash_Sales extends UniversalPost {

    int productId;

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

    public Flash_Sales(){}

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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public static final Parcelable.Creator<Flash_Sales> CREATOR = new Parcelable.Creator<Flash_Sales>() {
        public Flash_Sales createFromParcel(Parcel in) {
            return new Flash_Sales(in);
        }

        public Flash_Sales[] newArray(int size) {

            return new Flash_Sales[size];
        }

    };

    public Flash_Sales(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        productId        = in.readInt();
        id        = in.readInt();
        start_date     = in.readString();
        end_date       = in.readString();
        available_quantity     = in.readInt();
        reserved_quantity     = in.readInt();
        discount     = in.readInt();


    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeInt(id);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeInt(available_quantity);
        dest.writeInt(reserved_quantity);
        dest.writeInt(discount);

    }
    @Override
    public int describeContents() {
        return 0;
    }
}

package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderDiscount extends UniversalPost implements Parcelable {

    /*
    	"discount_name": "October Promotion - Lifree Any items 10% OFF(1.10.2020-31.10.2020)",
		"discount_type": "discount_percentage",
		"discount_amount": 820
     */

    @SerializedName("discount_name")
    private String discount_name;

    @SerializedName("discount_type")
    private String discount_type;

    @SerializedName("discount_amount")
    private int discount_amount;

    public OrderDiscount(){}

    protected OrderDiscount(Parcel in) {
        super();
        discount_name = in.readString();
        discount_type = in.readString();
        discount_amount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(discount_name);
        dest.writeString(discount_type);
        dest.writeInt(discount_amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderDiscount> CREATOR = new Creator<OrderDiscount>() {
        @Override
        public OrderDiscount createFromParcel(Parcel in) {
            return new OrderDiscount(in);
        }

        @Override
        public OrderDiscount[] newArray(int size) {
            return new OrderDiscount[size];
        }
    };

    public String getDiscount_name() {
        return discount_name;
    }

    public void setDiscount_name(String discount_name) {
        this.discount_name = discount_name;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public int getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(int discount_amount) {
        this.discount_amount = discount_amount;
    }


}

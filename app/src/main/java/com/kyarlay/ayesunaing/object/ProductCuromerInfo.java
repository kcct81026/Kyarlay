package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductCuromerInfo extends UniversalPost implements Parcelable {

    @SerializedName("discount_price")
    private int disCountPrice;

    @SerializedName("member_discount_price")
    private int memberDiscount;

    @SerializedName("flash_sale_discount")
    private int flashDiscount;

    @SerializedName("total_price")
    private int totalPrice;

    public ProductCuromerInfo(){}

    protected ProductCuromerInfo(Parcel in) {
        disCountPrice = in.readInt();
        memberDiscount = in.readInt();
        flashDiscount = in.readInt();
        totalPrice = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(disCountPrice);
        dest.writeInt(memberDiscount);
        dest.writeInt(flashDiscount);
        dest.writeInt(totalPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductCuromerInfo> CREATOR = new Creator<ProductCuromerInfo>() {
        @Override
        public ProductCuromerInfo createFromParcel(Parcel in) {
            return new ProductCuromerInfo(in);
        }

        @Override
        public ProductCuromerInfo[] newArray(int size) {
            return new ProductCuromerInfo[size];
        }
    };

    public int getDisCountPrice() {
        return disCountPrice;
    }

    public void setDisCountPrice(int disCountPrice) {
        this.disCountPrice = disCountPrice;
    }

    public int getMemberDiscount() {
        return memberDiscount;
    }

    public void setMemberDiscount(int memberDiscount) {
        this.memberDiscount = memberDiscount;
    }

    public int getFlashDiscount() {
        return flashDiscount;
    }

    public void setFlashDiscount(int flashDiscount) {
        this.flashDiscount = flashDiscount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

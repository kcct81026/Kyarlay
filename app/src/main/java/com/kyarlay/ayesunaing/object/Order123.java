package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ayesunaing on 9/16/16.
 */
public class Order123 extends  UniversalPost implements Parcelable {
    String orderId;
    String name;
    String phoneNo;
    String address;
    String orders;
    String uniqueIDs;
    int    deliveryID;
    int deliveryPrice;
    int totalPrice;
    String date;

    public Order123() {
    }

    protected Order123(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        orderId = in.readString();
        name = in.readString();
        phoneNo = in.readString();
        address = in.readString();
        orders = in.readString();
        uniqueIDs = in.readString();
        deliveryID = in.readInt();
        deliveryPrice = in.readInt();
        totalPrice = in.readInt();
        date = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(orderId);
        dest.writeString(name);
        dest.writeString(phoneNo);
        dest.writeString(address);
        dest.writeString(orders);
        dest.writeString(uniqueIDs);
        dest.writeInt(deliveryID);
        dest.writeInt(deliveryPrice);
        dest.writeInt(totalPrice);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order123> CREATOR = new Creator<Order123>() {
        @Override
        public Order123 createFromParcel(Parcel in) {
            return new Order123(in);
        }

        @Override
        public Order123[] newArray(int size) {
            return new Order123[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(int deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUniqueIDs() {
        return uniqueIDs;
    }

    public void setUniqueIDs(String uniqueIDs) {
        this.uniqueIDs = uniqueIDs;
    }


}
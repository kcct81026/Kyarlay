package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ayesunaing on 9/16/16.
 */
public class OrderIDs extends UniversalPost {
    int id, quantity, price;
    String option, title;


    public OrderIDs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static final Parcelable.Creator<OrderIDs> CREATOR = new Parcelable.Creator<OrderIDs>() {
        public OrderIDs createFromParcel(Parcel in) {
            return new OrderIDs(in);
        }

        public OrderIDs[] newArray(int size) {

            return new OrderIDs[size];
        }

    };

    public OrderIDs(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        id    = in.readInt();
        quantity = in.readInt();
        price  = in.readInt();
        option  = in.readString();
        title = in.readString();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(quantity);
        dest.writeInt(price);
        dest.writeString(option);
        dest.writeString(title);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}

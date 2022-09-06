package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StockSummeries extends UniversalPost {
    /*
    stock_summaries
     */

    @SerializedName("store_location_id")
    private int store_location_id;

    @SerializedName("quantity")
    private int quantity;

    public StockSummeries(){}


    public int getStore_location_id() {
        return store_location_id;
    }

    public void setStore_location_id(int store_location_id) {
        this.store_location_id = store_location_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static final Parcelable.Creator<StockSummeries> CREATOR = new Parcelable.Creator<StockSummeries>() {
        public StockSummeries createFromParcel(Parcel in) {
            return new StockSummeries(in);
        }

        public StockSummeries[] newArray(int size) {

            return new StockSummeries[size];
        }

    };

    public StockSummeries(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        store_location_id              = in.readInt();
        quantity      = in.readInt();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(store_location_id);
        dest.writeInt(quantity);

    }
    @Override
    public int describeContents() {
        return 0;
    }
}

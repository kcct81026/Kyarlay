package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Addresses extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("township_id")
    private int townShipID;

    @SerializedName("township_name")
    private String township_name;

    @SerializedName("address")
    private String addresses;

    @SerializedName("delivery_id")
    private int delivery_id;

    private int store_location_id;





    private boolean isSelected ;
    private boolean isCheckable;


    public Addresses(){}

    public int getStore_location_id() {
        return store_location_id;
    }

    public void setStore_location_id(int store_location_id) {
        this.store_location_id = store_location_id;
    }

    public int getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTownShipID() {
        return townShipID;
    }

    public void setTownShipID(int townShipID) {
        this.townShipID = townShipID;
    }

    public String getTownship_name() {
        return township_name;
    }

    public void setTownship_name(String township_name) {
        this.township_name = township_name;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isCheckable() {
        return isCheckable;
    }

    public void setCheckable(boolean checkable) {
        isCheckable = checkable;
    }

    public static final Parcelable.Creator<Addresses> CREATOR = new Parcelable.Creator<Addresses>() {
        public Addresses createFromParcel(Parcel in) {
            return new Addresses(in);
        }

        public Addresses[] newArray(int size) {

            return new Addresses[size];
        }

    };


    public Addresses(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        id = in.readInt();
        townShipID = in.readInt();
        delivery_id = in.readInt();
        store_location_id = in.readInt();
        township_name = in.readString();
        addresses = in.readString();
        isSelected = in.readByte() != 0;
        isCheckable = in.readByte() != 0;

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(townShipID);
        dest.writeInt(delivery_id);
        dest.writeInt(store_location_id);
        dest.writeString(township_name);
        dest.writeString(addresses);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeByte((byte) (isCheckable ? 1 : 0));

    }
    @Override
    public int describeContents() {
        return 0;
    }
}

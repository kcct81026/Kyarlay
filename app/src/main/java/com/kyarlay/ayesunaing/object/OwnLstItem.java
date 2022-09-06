package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

public class OwnLstItem extends UniversalPost implements Parcelable {

    int id;
    String title;
    int quantity;
    String info;

    public OwnLstItem(){}

    protected OwnLstItem(Parcel in) {
        super();
        id = in.readInt();
        title = in.readString();
        quantity = in.readInt();
        info = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(quantity);
        dest.writeString(info);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OwnLstItem> CREATOR = new Creator<OwnLstItem>() {
        @Override
        public OwnLstItem createFromParcel(Parcel in) {
            return new OwnLstItem(in);
        }

        @Override
        public OwnLstItem[] newArray(int size) {
            return new OwnLstItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

public class ChkLstItem extends UniversalPost implements Parcelable {

    int id;
    String title;
    int quantity;
    String url;
    String type;
    String info;

    public ChkLstItem(){}

    protected ChkLstItem(Parcel in) {
        super();
        id = in.readInt();
        title = in.readString();
        quantity = in.readInt();
        url = in.readString();
        type = in.readString();
        info = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(quantity);
        dest.writeString(url);
        dest.writeString(type);
        dest.writeString(info);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChkLstItem> CREATOR = new Creator<ChkLstItem>() {
        @Override
        public ChkLstItem createFromParcel(Parcel in) {
            return new ChkLstItem(in);
        }

        @Override
        public ChkLstItem[] newArray(int size) {
            return new ChkLstItem[size];
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

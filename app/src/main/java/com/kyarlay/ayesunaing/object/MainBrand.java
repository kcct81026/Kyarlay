package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainBrand extends UniversalPost {
    int pid;

    @SerializedName("type")
    String type;

    @SerializedName("title")
    String title;
    @SerializedName("end_at")
    String end_at;
    @SerializedName("url")
    String url;

    List<Brand> items = new ArrayList<>();

    public MainBrand() {

        postType = this.type;

    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Brand> getItems() {
        return items;
    }

    public void setItems(List<Brand> items) {
        this.items = items;
    }

    public static final Parcelable.Creator<MainBrand> CREATOR = new Parcelable.Creator<MainBrand>() {
        public MainBrand createFromParcel(Parcel in) {
            return new MainBrand(in);
        }

        public MainBrand[] newArray(int size) {

            return new MainBrand[size];
        }

    };

    public MainBrand(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        pid         = in.readInt();
        postType    = in.readString();
        title       = in.readString();
        end_at      = in.readString();
        url         = in.readString();
        in.readTypedList(items, Brand.CREATOR);

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeString(postType);
        dest.writeString(title);
        dest.writeString(end_at);
        dest.writeString(url);
        dest.writeTypedList(items);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}

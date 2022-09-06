package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayesu on 9/4/17.
 */

public class MainObject extends UniversalPost {
    int pid;

    int fullSize ;
    @SerializedName("type")
    String type;

    @SerializedName("title")
    String title;
    @SerializedName("end_at")
    String end_at;
    @SerializedName("url")
    String url;

    @SerializedName("items")
    List<MainItem> items = new ArrayList<>();

    public MainObject() {

        postType = this.type;

    }

    public int getFullSize() {
        return fullSize;
    }

    public void setFullSize(int fullSize) {
        this.fullSize = fullSize;
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

    public List<MainItem> getItems() {
        return items;
    }

    public void setItems(List<MainItem> items) {
        this.items = items;
    }

    public static final Parcelable.Creator<MainObject> CREATOR = new Parcelable.Creator<MainObject>() {
        public MainObject createFromParcel(Parcel in) {
            return new MainObject(in);
        }

        public MainObject[] newArray(int size) {

            return new MainObject[size];
        }

    };

    public MainObject(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        fullSize         = in.readInt();
        pid         = in.readInt();
        postType    = in.readString();
        title       = in.readString();
        end_at      = in.readString();
        url         = in.readString();
        in.readTypedList(items, MainItem.CREATOR);

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fullSize);
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

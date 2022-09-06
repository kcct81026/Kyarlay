package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesu on 9/4/17.
 */

public class MainItem extends UniversalPost {

    int pid, mainid;

    @SerializedName("id")
    int id;

    @SerializedName("url")
    String url;

    @SerializedName("title")
    String title;

    @SerializedName("dimen")
    int dimen;

    @SerializedName("preview_url")
    String preview_url;

    @SerializedName("post_type")
    String post_type;

    @SerializedName("open_target")
    String open_target;

    @SerializedName("discount_percentage")
    int discount_percentage;

    public MainItem() {
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getMainid() {
        return mainid;
    }

    public void setMainid(int mainid) {
        this.mainid = mainid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getOpen_target() {
        return open_target;
    }

    public void setOpen_target(String open_target) {
        this.open_target = open_target;
    }

    public int getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(int discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public static final Parcelable.Creator<MainItem> CREATOR = new Parcelable.Creator<MainItem>() {
        public MainItem createFromParcel(Parcel in) {
            return new MainItem(in);
        }

        public MainItem[] newArray(int size) {

            return new MainItem[size];
        }

    };

    public MainItem(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        pid                 = in.readInt();
        mainid              = in.readInt();
        id                  = in.readInt();
        url                 = in.readString();
        title               = in.readString();
        dimen               = in.readInt();
        preview_url         = in.readString();
        post_type           = in.readString();
        open_target         = in.readString();
        discount_percentage = in.readInt();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeInt(mainid);
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeInt(dimen);
        dest.writeString(preview_url);
        dest.writeString(post_type);
        dest.writeString(open_target);
        dest.writeInt(discount_percentage);
    }
    @Override
    public int describeContents() {
        return 0;
    }



}

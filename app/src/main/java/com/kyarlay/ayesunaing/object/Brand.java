package com.kyarlay.ayesunaing.object;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesunaing on 2/27/17.
 */

public class Brand extends UniversalPost {

    int pid;
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String title;
    @SerializedName("desc")
    String desc;
    @SerializedName("image")
    String imageUrl;

    @SerializedName("recommandation")
    String recommandation;

    String tag;

    public Brand() {
    }

    protected Brand(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        pid             = in.readInt();
        id              = in.readInt();
        imageUrl        = in.readString();
        title           = in.readString();
        desc            = in.readString();
        tag             = in.readString();
        recommandation  = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(pid);
        dest.writeInt(id);
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(tag);
        dest.writeString(recommandation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Brand> CREATOR = new Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel in) {
            return new Brand(in);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getPostType() {
        return postType;
    }

    @Override
    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRecommandation() {
        return recommandation;
    }

    public void setRecommandation(String recommandation) {
        this.recommandation = recommandation;
    }
}

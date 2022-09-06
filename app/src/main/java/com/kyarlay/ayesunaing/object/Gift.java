package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Gift extends  UniversalPost implements Parcelable {


    @SerializedName("id")
    int id;
    @SerializedName("post_type")
    String post_type;
    @SerializedName("status")
    String status;
    @SerializedName("release_at")
    String release_at;
    @SerializedName("title")
    String title;
    @SerializedName("body")
    String body;
    @SerializedName("photo_url")
    String photo_url;
    @SerializedName("dimen")
    int dimen;
    @SerializedName("available_count")
    int available_count;
    @SerializedName("redeemed_count")
    int redeemed_count;
    @SerializedName("price")
    int price;





    public Gift() {
    }

    protected Gift(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id              = in.readInt();
        post_type       = in.readString();
        status          = in.readString();
        release_at      = in.readString();
        title           = in.readString();
        body            = in.readString();
        photo_url       = in.readString();
        dimen           = in.readInt();
        available_count = in.readInt();
        redeemed_count  = in.readInt();
        price           = in.readInt();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(post_type);
        dest.writeString(status);
        dest.writeString(release_at);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(photo_url);
        dest.writeInt(dimen);
        dest.writeInt(available_count);
        dest.writeInt(redeemed_count);
        dest.writeInt(price);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Gift> CREATOR = new Parcelable.Creator<Gift>() {
        @Override
        public Gift createFromParcel(Parcel in) {
            return new Gift(in);
        }

        @Override
        public Gift[] newArray(int size) {
            return new Gift[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelease_at() {
        return release_at;
    }

    public void setRelease_at(String release_at) {
        this.release_at = release_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
    }

    public int getAvailable_count() {
        return available_count;
    }

    public void setAvailable_count(int available_count) {
        this.available_count = available_count;
    }

    public int getRedeemed_count() {
        return redeemed_count;
    }

    public void setRedeemed_count(int redeemed_count) {
        this.redeemed_count = redeemed_count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}


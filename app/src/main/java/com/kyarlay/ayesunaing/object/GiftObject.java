package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GiftObject extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("status")
    private String status;

    @SerializedName("release_at")
    private String release_at;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("photo_url")
    private String photo_url;

    @SerializedName("dimen")
    private int dimen;

    @SerializedName("available_count")
    private int available_count;

    @SerializedName("redeemed_count")
    private int redeemed_count;

    @SerializedName("price")
    private int price;

    @SerializedName("phone")
    private String phone;

    @SerializedName("access")
    private String access;

    @SerializedName("end_at")
    private String end_at;

    @SerializedName("prompt_message")
    private String prompt_message;

    public GiftObject(){}

    public int getId() {
        return id;
    }

    public String getPrompt_message() {
        return prompt_message;
    }

    public void setPrompt_message(String prompt_message) {
        this.prompt_message = prompt_message;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public static final Parcelable.Creator<GiftObject> CREATOR = new Parcelable.Creator<GiftObject>() {
        public GiftObject createFromParcel(Parcel in) {
            return new GiftObject(in);
        }

        public GiftObject[] newArray(int size) {

            return new GiftObject[size];
        }

    };

    public GiftObject(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {

        id              = in.readInt();
        status          = in.readString();
        release_at      = in.readString();
        title           = in.readString();
        body            = in.readString();
        photo_url       = in.readString();
        dimen           = in.readInt();
        available_count = in.readInt();
        redeemed_count  = in.readInt();
        price           = in.readInt();
        phone           = in.readString();
        access          = in.readString();
        postType        = in.readString();
        end_at          = in.readString();
        prompt_message  = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(status);
        dest.writeString(release_at);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(photo_url);
        dest.writeInt(dimen);
        dest.writeInt(available_count);
        dest.writeInt(redeemed_count);
        dest.writeInt(price);
        dest.writeString(phone);
        dest.writeString(access);
        dest.writeString(postType);
        dest.writeString(end_at);
        dest.writeString(prompt_message);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}

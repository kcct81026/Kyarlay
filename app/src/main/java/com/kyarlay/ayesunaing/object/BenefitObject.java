package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BenefitObject extends UniversalPost implements Parcelable {

    @SerializedName("body")
    private String body;
    @SerializedName("status")
    private boolean status;
    @SerializedName("img_url")
   private String img_url;

    BenefitObject(){}

    protected BenefitObject(Parcel in) {

        body = in.readString();
        status = in.readByte() != 0;
        img_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(body);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(img_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BenefitObject> CREATOR = new Creator<BenefitObject>() {
        @Override
        public BenefitObject createFromParcel(Parcel in) {
            return new BenefitObject(in);
        }

        @Override
        public BenefitObject[] newArray(int size) {
            return new BenefitObject[size];
        }
    };

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}

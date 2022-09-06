package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ChildGrowthObj extends UniversalPost {

    @SerializedName("id")
    private int id ;

    @SerializedName("type")
    private String type;

    @SerializedName("period")
    private int period;

    @SerializedName("min_height")
    private String min_height;

    @SerializedName("max_height")
    private String max_height;

    @SerializedName("min_weight")
    private String min_weight;


    @SerializedName("max_weight")
    private String max_weight;

    @SerializedName("desc")
    private String desc;

    @SerializedName("photo")
    private String photo;

    @SerializedName("height")
    private String height;

    @SerializedName("weight")
    private String weight;


    @SerializedName("photo_dimen")
    private int photo_dimen;

    @SerializedName("post")
    private Reading reading;

    public ChildGrowthObj(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getMin_height() {
        return min_height;
    }

    public void setMin_height(String min_height) {
        this.min_height = min_height;
    }

    public String getMax_height() {
        return max_height;
    }

    public void setMax_height(String max_height) {
        this.max_height = max_height;
    }

    public String getMin_weight() {
        return min_weight;
    }

    public void setMin_weight(String min_weight) {
        this.min_weight = min_weight;
    }

    public String getMax_weight() {
        return max_weight;
    }

    public void setMax_weight(String max_weight) {
        this.max_weight = max_weight;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPhoto_dimen() {
        return photo_dimen;
    }

    public void setPhoto_dimen(int photo_dimen) {
        this.photo_dimen = photo_dimen;
    }

    public Reading getReading() {
        return reading;
    }

    public void setReading(Reading reading) {
        this.reading = reading;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public static final Parcelable.Creator<ChildGrowthObj> CREATOR = new Parcelable.Creator<ChildGrowthObj>() {
        public ChildGrowthObj createFromParcel(Parcel in) {
            return new ChildGrowthObj(in);
        }

        public ChildGrowthObj[] newArray(int size) {

            return new ChildGrowthObj[size];
        }

    };

    public ChildGrowthObj(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        id        = in.readInt();
        type        = in.readString();
        period        = in.readInt();
        min_height        = in.readString();
        max_height        = in.readString();
        min_weight        = in.readString();
        max_weight        = in.readString();
        weight        = in.readString();
        height        = in.readString();
        desc        = in.readString();
        photo        = in.readString();
        photo_dimen        = in.readInt();
        reading            = in. readParcelable(Reading.class.getClassLoader());


    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeInt(period);
        dest.writeString(min_height);
        dest.writeString(max_height);
        dest.writeString(min_weight);
        dest.writeString(max_weight);
        dest.writeString(weight);
        dest.writeString(height);
        dest.writeString(desc);
        dest.writeString(photo);
        dest.writeInt(photo_dimen);
        dest.writeParcelable(reading, flags);
    }
    @Override
    public int describeContents() {
        return 0;
    }


}

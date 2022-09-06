package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LuckyDay extends  UniversalPost implements Parcelable {


    @SerializedName("day_info")
    String day_info;

    @SerializedName("date_info")
    String date_info;

    @SerializedName("luck_group")
    int luck_group;

    @SerializedName("birth_group")
    String birth_group;

    @SerializedName("work_group")
    String work_group;

    @SerializedName("desc")
    String desc;



    public LuckyDay() {
    }

    protected LuckyDay(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        day_info        = in.readString();
        date_info       = in.readString();
        luck_group      = in.readInt();
        birth_group     = in.readString();
        work_group      = in.readString();
        desc            = in.readString();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(day_info);
        dest.writeString(date_info);
        dest.writeInt(luck_group);
        dest.writeString(birth_group);
        dest.writeString(work_group);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LuckyDay> CREATOR = new Creator<LuckyDay>() {
        @Override
        public LuckyDay createFromParcel(Parcel in) {
            return new LuckyDay(in);
        }

        @Override
        public LuckyDay[] newArray(int size) {
            return new LuckyDay[size];
        }
    };


    public String getDay_info() {
        return day_info;
    }

    public void setDay_info(String day_info) {
        this.day_info = day_info;
    }

    public String getDate_info() {
        return date_info;
    }

    public void setDate_info(String date_info) {
        this.date_info = date_info;
    }

    public int getLuck_group() {
        return luck_group;
    }

    public void setLuck_group(int luck_group) {
        this.luck_group = luck_group;
    }

    public String getBirth_group() {
        return birth_group;
    }

    public void setBirth_group(String birth_group) {
        this.birth_group = birth_group;
    }

    public String getWork_group() {
        return work_group;
    }

    public void setWork_group(String work_group) {
        this.work_group = work_group;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

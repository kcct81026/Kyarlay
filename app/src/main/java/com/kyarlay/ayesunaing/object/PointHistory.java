package com.kyarlay.ayesunaing.object;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PointHistory extends UniversalPost {

    @SerializedName("created_at")
    String created_at;
    @SerializedName("usage_type")
    String usage_type;
    @SerializedName("point")
    int point;

    public PointHistory() {
    }

    protected PointHistory(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {

        created_at  = in.readString();
        usage_type  = in.readString();
        point       = in.readInt();
        postType    = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(created_at);
        dest.writeString(usage_type);
        dest.writeInt(point);
        dest.writeString(postType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PointHistory> CREATOR = new Creator<PointHistory>() {
        @Override
        public PointHistory createFromParcel(Parcel in) {
            return new PointHistory(in);
        }

        @Override
        public PointHistory[] newArray(int size) {
            return new PointHistory[size];
        }
    };


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(String usage_type) {
        this.usage_type = usage_type;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String getPostType() {
        return postType;
    }

    @Override
    public void setPostType(String postType) {
        this.postType = postType;
    }

}

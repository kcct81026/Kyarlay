package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesu on 7/24/17.
 */

public class Campaign extends UniversalPost {
    public int pid;
    @SerializedName("id")
    public int id;
    @SerializedName("start_at")
    String start;
    @SerializedName("end_at")
    String end;
    @SerializedName("title")
    String title;
    @SerializedName("photo_url")
    String url;
    @SerializedName("dimen")
    int dimen;

    public Campaign() {
    }

    protected Campaign(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        pid     = in.readInt();
        id      = in.readInt();
        start   = in.readString();
        end     = in.readString();
        title   = in.readString();
        url     = in.readString();
        dimen   = in.readInt();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(pid);
        dest.writeInt(id);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeInt(dimen);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Campaign> CREATOR = new Parcelable.Creator<Campaign>() {
        @Override
        public Campaign createFromParcel(Parcel in) {
            return new Campaign(in);
        }

        @Override
        public Campaign[] newArray(int size) {
            return new Campaign[size];
        }
    };

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
    }

    public static Parcelable.Creator<Campaign> getCREATOR() {
        return CREATOR;
    }
}

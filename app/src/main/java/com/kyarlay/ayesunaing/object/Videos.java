package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Videos extends UniversalPost implements Parcelable {


    @SerializedName("id")
    int id;
    @SerializedName("program_id")
    String program_id;
    @SerializedName("youtube_id")
    String youtube_id;
    @SerializedName("title")
    String title;
    @SerializedName("desc")
    String desc;
    @SerializedName("dimen")
    int dimen;
    @SerializedName("preview_url")
    String preview_url;
    @SerializedName("ads_photo_url")
    String ads_photo_url;

    @SerializedName("ads_photo_dimen")
    int ads_photo_dimen;
    @SerializedName("ads_url")
    String ads_url;
    @SerializedName("ads_type")
    String ads_type;

    @SerializedName("post_type")
    String postType;

    int nowPlaying;



    public Videos () {
    }

    protected Videos(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id              = in.readInt();
        program_id      = in.readString();
        youtube_id      = in.readString();
        title           = in.readString();
        desc            = in.readString();
        dimen           = in.readInt();
        preview_url     = in.readString();
        postType        = in.readString();
        nowPlaying      = in.readInt();
        ads_photo_url      = in.readString();
        ads_photo_dimen      = in.readInt();
        ads_url      = in.readString();
        ads_type      = in.readString();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(program_id);
        dest.writeString(youtube_id);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeInt(dimen);
        dest.writeString(preview_url);
        dest.writeString(postType);
        dest.writeInt(nowPlaying);
        dest.writeString(ads_photo_url);
        dest.writeInt(ads_photo_dimen);
        dest.writeString(ads_url);
        dest.writeString(ads_type);
    }

    public String getAds_photo_url() {
        return ads_photo_url;
    }

    public void setAds_photo_url(String ads_photo_url) {
        this.ads_photo_url = ads_photo_url;
    }

    public int getAds_photo_dimen() {
        return ads_photo_dimen;
    }

    public void setAds_photo_dimen(int ads_photo_dimen) {
        this.ads_photo_dimen = ads_photo_dimen;
    }

    public String getAds_url() {
        return ads_url;
    }

    public void setAds_url(String ads_url) {
        this.ads_url = ads_url;
    }

    public String getAds_type() {
        return ads_type;
    }

    public void setAds_type(String ads_type) {
        this.ads_type = ads_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };

    public String getProgram_id() {
        return program_id;
    }

    public void setProgram_id(String program_id) {
        this.program_id = program_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    @Override
    public String getPostType() {
        return postType;
    }

    @Override
    public void setPostType(String postType) {
        this.postType = postType;
    }

    public int getNowPlaying() {
        return nowPlaying;
    }

    public void setNowPlaying(int nowPlaying) {
        this.nowPlaying = nowPlaying;
    }
}


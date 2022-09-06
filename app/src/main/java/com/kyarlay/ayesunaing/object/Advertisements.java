package com.kyarlay.ayesunaing.object;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kcct on 9/5/16.
 */
public class Advertisements extends UniversalPost {
    int id;
    String url;
    String title;
    int dimen;
    String media_url;
    String preview_url;
    String post_type;
    String open_target;

    public Advertisements(){}

    public Advertisements(int id, String url, String title, int dimen, String media_url, String preview_url, String post_type, String open_target) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.dimen = dimen;
        this.media_url = media_url;
        this.preview_url = preview_url;
        this.post_type = post_type;
        this.open_target = open_target;
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

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
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

    public static final Parcelable.Creator<Advertisements> CREATOR = new Parcelable.Creator<Advertisements>() {
        public Advertisements createFromParcel(Parcel in) {
            return new Advertisements(in);
        }

        public Advertisements[] newArray(int size) {

            return new Advertisements[size];
        }

    };

    public Advertisements(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id          = in.readInt();
        url         = in.readString();
        title       = in.readString();
        dimen       = in.readInt();
        media_url   = in.readString();
        preview_url = in.readString();
        post_type   = in.readString();
        open_target = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeInt(dimen);
        dest.writeString(media_url);
        dest.writeString(preview_url);
        dest.writeString(post_type);
        dest.writeString(open_target);
    }
    @Override
    public int describeContents() {
        return 0;
    }

}

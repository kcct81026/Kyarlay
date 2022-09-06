package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesu on 7/14/17.
 */

public class Reading_Post extends UniversalPost {
    int pid;
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("post_type")
    String post_type;
    @SerializedName("body")
    String body;
    @SerializedName("preview_url")
    String preview_url;
    @SerializedName("dimen")
    int dimen;
    @SerializedName("youtube_id")
    String youtube_id;

    @SerializedName("product")
    Product product;

    public Reading_Post() {
    }


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

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
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

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static final Parcelable.Creator<Reading_Post> CREATOR = new Parcelable.Creator<Reading_Post>() {
        public Reading_Post createFromParcel(Parcel in) {
            return new Reading_Post(in);
        }

        public Reading_Post[] newArray(int size) {

            return new Reading_Post[size];
        }

    };

    public Reading_Post(Parcel in) {
        super();


        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        pid               = in.readInt();
        id                  = in.readInt();
        postType            = in.readString();
        post_type           = in.readString();
        title               = in.readString();
        body                = in.readString();
        preview_url         = in.readString();
        dimen               = in.readInt();
        youtube_id          = in.readString();
        product            = in. readParcelable(Product.class.getClassLoader());

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeInt(id);
        dest.writeString(postType);
        dest.writeString(post_type);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(preview_url);
        dest.writeInt(dimen);
        dest.writeString(youtube_id);
        dest.writeParcelable(product, flags);


    }
    @Override
    public int describeContents() {
        return 0;
    }

}

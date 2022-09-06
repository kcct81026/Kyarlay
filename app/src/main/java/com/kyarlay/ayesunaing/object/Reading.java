package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayesu on 7/14/17.
 */

public class Reading extends UniversalPost {
    int pid;
    @SerializedName("id")
    int id;
    @SerializedName("release_at")
    String release_at;
    @SerializedName("sort_by")
    String sort_by;
    @SerializedName("url")
    String url;
    @SerializedName("preview_image_url")
    String preview_image_url;
    @SerializedName("photo_url")
    String photo_url;
    @SerializedName("dimen")
    int dimen;
    @SerializedName("title")
    String title;
    @SerializedName("body")
    String body;
    @SerializedName("media_format")
    String media_format;
    @SerializedName("category_type")
    String category_type;
    @SerializedName("likes")
    int likes;
    @SerializedName("comments_count")
    int comment_coount;
    @SerializedName("page_name")
    String page_name;
    @SerializedName("page_img_url")
    String page_img_url;
    @SerializedName("birth_month")
    int birth_month;
    @SerializedName("birth_year")
    int birth_year;
    @SerializedName("customer_id")
    int customer_id;

    @SerializedName("customer_type")
    String customer_type;


    @SerializedName("post_type")
    String postType;

    @SerializedName("posts")
    List<Reading_Post> posts = new ArrayList<>();

    public Reading() {
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    @Override
    public String getPostType() {
        return postType;
    }

    @Override
    public void setPostType(String postType) {
        this.postType = postType;
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

    public String getRelease_at() {
        return release_at;
    }

    public void setRelease_at(String release_at) {
        this.release_at = release_at;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreview_image_url() {
        return preview_image_url;
    }

    public void setPreview_image_url(String preview_image_url) {
        this.preview_image_url = preview_image_url;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getMedia_format() {
        return media_format;
    }

    public void setMedia_format(String media_format) {
        this.media_format = media_format;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public List<Reading_Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Reading_Post> posts) {
        this.posts = posts;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getPage_img_url() {
        return page_img_url;
    }

    public void setPage_img_url(String page_img_url) {
        this.page_img_url = page_img_url;
    }

    public int getComment_coount() {
        return comment_coount;
    }

    public void setComment_coount(int comment_coount) {
        this.comment_coount = comment_coount;
    }

    public int getBirth_month() {
        return birth_month;
    }

    public void setBirth_month(int birth_month) {
        this.birth_month = birth_month;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public static final Parcelable.Creator<Reading> CREATOR = new Parcelable.Creator<Reading>() {
        public Reading createFromParcel(Parcel in) {
            return new Reading(in);
        }
        public Reading[] newArray(int size) {
            return new Reading[size];
        }
    };

    public Reading(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        pid                 = in.readInt();
        likes               = in.readInt();
        comment_coount      = in.readInt();
        id                  = in.readInt();
        release_at          = in.readString();
        sort_by             = in.readString();
        url                 = in.readString();
        preview_image_url   = in.readString();
        photo_url           = in.readString();
        dimen               = in.readInt();
        title               = in.readString();
        body                = in.readString();
        media_format        = in.readString();
        category_type       = in.readString();
        page_name           = in.readString();
        page_img_url        = in.readString();
        birth_month         = in.readInt();
        birth_year          = in.readInt();
        customer_id         = in.readInt();
        postType            = in.readString();
        customer_type       = in.readString();

        in.readTypedList(posts, Reading_Post.CREATOR);

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeInt(likes);
        dest.writeInt(comment_coount);
        dest.writeInt(id);
        dest.writeString(release_at);
        dest.writeString(sort_by);
        dest.writeString(url);
        dest.writeString(preview_image_url);
        dest.writeString(photo_url);
        dest.writeInt(dimen);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(media_format);
        dest.writeString(category_type);
        dest.writeString(page_name);
        dest.writeString(page_img_url);
        dest.writeTypedList(posts);
        dest.writeInt(birth_month);
        dest.writeInt(birth_year);
        dest.writeInt(customer_id);
        dest.writeString(postType);
        dest.writeString(customer_type);


    }
    @Override
    public int describeContents() {
        return 0;
    }

}
package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesu on 1/29/18.
 */

public class Comment extends UniversalPost {


    @SerializedName("id")
    int id;
    @SerializedName("commentor_id")
    int commentor_id;

    @SerializedName("commentor")
    String commentor;
    @SerializedName("body")
    String body;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("title")
    String title;

    @SerializedName("commentor_profile_url")
    String commentor_profile;

    @SerializedName("parent_status")
    String parent_status;

    @SerializedName("kid_gender")
    String kid_gender;

    @SerializedName("birth_month")
    int birth_month;

    @SerializedName("birth_year")
    int birth_year;

    @SerializedName("comment_type")
    String comment_type;

    @SerializedName("product_id")
    int product_id;

    @SerializedName("post_id")
    int post_id;

    @SerializedName("subtitle")
    String subtitle;

    @SerializedName("price")
    int price;

    @SerializedName("preview_img_url")
    String preview_img_url;

    @SerializedName("img_url")
    String comment_photo_url;

    @SerializedName("img_dimen")
    int comment_dimen;

    @SerializedName("commentor_type")
    String commentor_type;

    public String getCommentor_type() {
        return commentor_type;
    }

    public void setCommentor_type(String commentor_type) {
        this.commentor_type = commentor_type;
    }

    public String getComment_type() {
        return comment_type;
    }

    public void setComment_type(String comment_type) {
        this.comment_type = comment_type;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPreview_img_url() {
        return preview_img_url;
    }

    public void setPreview_img_url(String preview_img_url) {
        this.preview_img_url = preview_img_url;
    }

    public String getCommentor_profile() {
        return commentor_profile;
    }

    public void setCommentor_profile(String commentor_profile) {
        this.commentor_profile = commentor_profile;
    }

    public int getComment_dimen() {
        return comment_dimen;
    }

    public void setComment_dimen(int comment_dimen) {
        this.comment_dimen = comment_dimen;
    }

    public String getComment_photo_url() {
        return comment_photo_url;
    }

    public void setComment_photo_url(String comment_photo_url) {
        this.comment_photo_url = comment_photo_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentor_id() {
        return commentor_id;
    }

    public void setCommentor_id(int commentor_id) {
        this.commentor_id = commentor_id;
    }

    public String getCommentor() {
        return commentor;
    }

    public void setCommentor(String commentor) {
        this.commentor = commentor;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent_status() {
        return parent_status;
    }

    public void setParent_status(String parent_status) {
        this.parent_status = parent_status;
    }

    public String getKid_gender() {
        return kid_gender;
    }

    public void setKid_gender(String kid_gender) {
        this.kid_gender = kid_gender;
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

    public Comment() {
    }

    public Comment(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        public Comment[] newArray(int size) {

            return new Comment[size];
        }

    };

    private void readFromParcel(Parcel in) {
        id              = in.readInt();
        commentor       = in.readString();
        body            = in.readString();
        created_at      = in.readString();
        commentor_id    = in.readInt();
        title           = in.readString();
        parent_status   = in.readString();
        kid_gender      = in.readString();
        commentor_profile      = in.readString();
        birth_month     = in.readInt();
        birth_year      = in.readInt();
        comment_dimen      = in.readInt();
        comment_photo_url      = in.readString();
        comment_type      = in.readString();
        product_id      = in.readInt();
        post_id      = in.readInt();
        subtitle      = in.readString();
        price      = in.readInt();
        preview_img_url      = in.readString();
        commentor_type      = in.readString();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(commentor);
        dest.writeString(body);
        dest.writeString(created_at);
        dest.writeInt(commentor_id);
        dest.writeString(title);
        dest.writeString(parent_status);
        dest.writeString(kid_gender);
        dest.writeString(commentor_profile);
        dest.writeInt(birth_month);
        dest.writeInt(birth_year);
        dest.writeInt(comment_dimen);
        dest.writeString(comment_photo_url);
        dest.writeString(comment_type);
        dest.writeInt(product_id);
        dest.writeInt(post_id);
        dest.writeString(subtitle);
        dest.writeInt(price);
        dest.writeString(preview_img_url);
        dest.writeString(commentor_type);

    }
    @Override
    public int describeContents() {
        return 0;
    }


}

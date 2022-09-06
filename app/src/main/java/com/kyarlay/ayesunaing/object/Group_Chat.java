package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Group_Chat extends UniversalPost {

    int id;
    @SerializedName("post_id")
    int post_id;
    @SerializedName("like_count")
    int like_count;
    @SerializedName("comment_count")
    int comment_count;

    @SerializedName("title")
    String title;
    @SerializedName("baby_detail")
    String baby_detail;
    @SerializedName("date")
    String date;
    @SerializedName("question")
    String question;
    @SerializedName("url")
    String url;


    public Group_Chat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBaby_detail() {
        return baby_detail;
    }

    public void setBaby_detail(String baby_detail) {
        this.baby_detail = baby_detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static final Parcelable.Creator<Group_Chat> CREATOR = new Parcelable.Creator<Group_Chat>() {
        public Group_Chat createFromParcel(Parcel in) {
            return new Group_Chat(in);
        }

        public Group_Chat[] newArray(int size) {

            return new Group_Chat[size];
        }

    };

    public Group_Chat(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {

        id              = in.readInt();
        post_id         = in.readInt();
        like_count      = in.readInt();
        comment_count   = in.readInt();
        title           = in.readString();
        baby_detail     = in.readString();
        date            = in.readString();
        question        = in.readString();
        postType        = in.readString();
        url             = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeInt(post_id);
        dest.writeInt(like_count);
        dest.writeInt(comment_count);
        dest.writeString(title);
        dest.writeString(baby_detail);
        dest.writeString(date);
        dest.writeString(question);
        dest.writeString(postType);
        dest.writeString(url);
    }
    @Override
    public int describeContents() {
        return 0;
    }


}

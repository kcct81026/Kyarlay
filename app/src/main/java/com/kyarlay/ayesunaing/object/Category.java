package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class Category extends UniversalPost {

    int priId, status;
    @SerializedName("id")
    int id;
    @SerializedName("main_cat_id")
    int main_id;
    @SerializedName("tag")
    String tag;

    @SerializedName("name")
    String name;



    public Category() {

    }


    public int getMain_id() {
        return main_id;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriId() {
        return priId;
    }

    public void setPriId(int priId) {
        this.priId = priId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {

            return new Category[size];
        }

    };

    public Category(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        id      = in.readInt();
        main_id = in.readInt();
        tag     = in.readString();
        name    = in.readString();
        status  = in.readInt();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(main_id   );
        dest.writeString(tag);
        dest.writeString(name);
        dest.writeInt(status);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}

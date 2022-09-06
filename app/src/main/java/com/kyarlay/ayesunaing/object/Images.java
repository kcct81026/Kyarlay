package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class Images extends UniversalPost{

    int id, productId;
    @SerializedName("dimen")
    int dimen;
    @SerializedName("url")
    String url;

    @SerializedName("name")
    String name;

    int imgVisible ;



    public Images() {
    }

    public int getImgVisible() {
        return imgVisible;
    }

    public void setImgVisible(int imgVisible) {
        this.imgVisible = imgVisible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
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

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        public Images[] newArray(int size) {

            return new Images[size];
        }

    };

    public Images(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        id          = in.readInt();
        url         = in.readString();
        dimen       = in.readInt();
        productId   = in.readInt();
        name        = in.readString();
        imgVisible        = in.readInt();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeInt(dimen);
        dest.writeInt(productId);
        dest.writeString(name);
        dest.writeInt(imgVisible);
    }
    @Override
    public int describeContents() {
        return 0;
    }


}


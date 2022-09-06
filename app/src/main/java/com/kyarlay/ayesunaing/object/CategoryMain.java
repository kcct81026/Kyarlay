package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesunaing on 11/1/16.
 */

public class CategoryMain extends UniversalPost implements Parcelable {


    int priId;
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String title;
    @SerializedName("tag")
    String tag;
    @SerializedName("image")
    String image;
    @SerializedName("image_big")
    String image_big;

    @SerializedName("inactive_image")
    String inactive_image;

    String category_type;

    String iconBackgroundColor;
    String textColor;
    String textBackgroundColor;
    String backgroundColor;
    boolean isChoosen;

    @SerializedName("header_image")
    String header_image;

    public CategoryMain(){}


    public CategoryMain(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        priId = in.readInt();
        id = in.readInt();
        title = in.readString();
        tag = in.readString();
        image = in.readString();
        image_big = in.readString();
        inactive_image = in.readString();
        category_type = in.readString();
        iconBackgroundColor = in.readString();
        textColor = in.readString();
        textBackgroundColor = in.readString();
        backgroundColor = in.readString();
        header_image = in.readString();
        isChoosen = in.readByte() != 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(priId);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(tag);
        dest.writeString(image);
        dest.writeString(image_big);
        dest.writeString(inactive_image);
        dest.writeString(category_type);
        dest.writeString(iconBackgroundColor);
        dest.writeString(textColor);
        dest.writeString(textBackgroundColor);
        dest.writeString(backgroundColor);
        dest.writeString(header_image);
        dest.writeByte((byte) (isChoosen ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryMain> CREATOR = new Creator<CategoryMain>() {
        @Override
        public CategoryMain createFromParcel(Parcel in) {
            return new CategoryMain(in);
        }

        @Override
        public CategoryMain[] newArray(int size) {
            return new CategoryMain[size];
        }
    };

    public boolean isChoosen() {
        return isChoosen;
    }

    public void setChoosen(boolean choosen) {
        isChoosen = choosen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_big() {
        return image_big;
    }

    public void setImage_big(String image_big) {
        this.image_big = image_big;
    }

    public String getInactive_image() {
        return inactive_image;
    }

    public void setInactive_image(String inactive_image) {
        this.inactive_image = inactive_image;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public String getIconBackgroundColor() {
        return iconBackgroundColor;
    }

    public void setIconBackgroundColor(String iconBackgroundColor) {
        this.iconBackgroundColor = iconBackgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTextBackgroundColor() {
        return textBackgroundColor;
    }

    public void setTextBackgroundColor(String textBackgroundColor) {
        this.textBackgroundColor = textBackgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getHeader_image() {
        return header_image;
    }

    public void setHeader_image(String header_image) {
        this.header_image = header_image;
    }


}

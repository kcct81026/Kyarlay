package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainCategoryObj extends UniversalPost {

    int priId;
    @SerializedName("id")
    int id;

    @SerializedName("mm")
    String mm;


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

    @SerializedName("header_image")
    String header_image;

    @SerializedName("header_image_dimen")
    int header_image_dimen;



    @SerializedName("categories")
    List<CategoryMain> categoryMainList = new ArrayList<>();


    @SerializedName("template_type")
    String category_type;

    @SerializedName("icon_bg_color")
    String iconBackgroundColor;

    @SerializedName("text_color")
    String textColor;


    @SerializedName("text_bg_color")
    String textBackgroundColor;


    @SerializedName("bg_color")
    String backgroundColor;

    int showTitle = 0;


    public MainCategoryObj() {
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public int getPriId() {
        return priId;
    }

    public void setPriId(int priId) {
        this.priId = priId;
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

    public String getHeader_image() {
        return header_image;
    }

    public void setHeader_image(String header_image) {
        this.header_image = header_image;
    }

    public int getHeader_image_dimen() {
        return header_image_dimen;
    }

    public void setHeader_image_dimen(int header_image_dimen) {
        this.header_image_dimen = header_image_dimen;
    }

    public List<CategoryMain> getCategoryMainList() {
        return categoryMainList;
    }

    public void setCategoryMainList(List<CategoryMain> categoryMainList) {
        this.categoryMainList = categoryMainList;
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

    public int getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(int showTitle) {
        this.showTitle = showTitle;
    }

    public static final Parcelable.Creator<CategoryMain> CREATOR = new Parcelable.Creator<CategoryMain>() {
        public CategoryMain createFromParcel(Parcel in) {
            return new CategoryMain(in);
        }

        public CategoryMain[] newArray(int size) {

            return new CategoryMain[size];
        }

    };

    public MainCategoryObj(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        id        = in.readInt();
        mm        = in.readString();
        title     = in.readString();
        tag       = in.readString();
        image     = in.readString();
        image_big = in.readString();
        inactive_image = in.readString();
        in.readTypedList(categoryMainList, CategoryMain.CREATOR);
        header_image = in.readString();
        header_image_dimen = in.readInt();
        category_type = in.readString();
        backgroundColor = in.readString();
        iconBackgroundColor = in.readString();
        textColor = in.readString();
        textBackgroundColor = in.readString();
        showTitle = in.readInt();



    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(mm);
        dest.writeString(title);
        dest.writeString(tag);
        dest.writeString(image);
        dest.writeString(image_big);
        dest.writeString(inactive_image);
        dest.writeTypedList(categoryMainList);
        dest.writeString(header_image);
        dest.writeInt(header_image_dimen);
        dest.writeString(category_type);
        dest.writeString(backgroundColor);
        dest.writeString(iconBackgroundColor);
        dest.writeString(textColor);
        dest.writeString(textBackgroundColor);
        dest.writeInt(showTitle);

    }
    @Override
    public int describeContents() {
        return 0;
    }
}

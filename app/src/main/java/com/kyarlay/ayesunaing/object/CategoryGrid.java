package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class CategoryGrid extends UniversalPost {

   private String type;

   private int id;

   private int showMore;

    String category_type;
    String tag;
    int heightTextView;

    String iconBackgroundColor;
    String textColor;
    String textBackgroundColor;
    String backgroundColor;


    public int getHeightTextView() {
        return heightTextView;
    }

    public void setHeightTextView(int heightTextView) {
        this.heightTextView = heightTextView;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    private List<CategoryMain> categoryMainList = new ArrayList<>();

   public CategoryGrid(){
       postType = this.type;
   }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getShowMore() {
        return showMore;
    }

    public void setShowMore(int showMore) {
        this.showMore = showMore;
    }

    public List<CategoryMain> getCategoryMainList() {
        return categoryMainList;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }


    public void setCategoryMainList(List<CategoryMain> categoryMainList) {
        this.categoryMainList = categoryMainList;
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

    public static final Parcelable.Creator<CategoryGrid> CREATOR = new Parcelable.Creator<CategoryGrid>() {
        public CategoryGrid createFromParcel(Parcel in) {
            return new CategoryGrid(in);
        }

        public CategoryGrid[] newArray(int size) {

            return new CategoryGrid[size];
        }

    };

    public CategoryGrid(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {

        postType    = in.readString();
        id    = in.readInt();
        showMore    = in.readInt();
        in.readTypedList(categoryMainList, CategoryMain.CREATOR);
        category_type = in.readString();
        tag = in.readString();
        heightTextView = in.readInt();
        backgroundColor = in.readString();
        iconBackgroundColor = in.readString();
        textColor = in.readString();
        textBackgroundColor = in.readString();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(postType);
        dest.writeInt(id);
        dest.writeInt(showMore);
        dest.writeTypedList(categoryMainList);
        dest.writeString(category_type);
        dest.writeString(tag);
        dest.writeInt(heightTextView);
        dest.writeString(backgroundColor);
        dest.writeString(iconBackgroundColor);
        dest.writeString(textColor);
        dest.writeString(textBackgroundColor);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}

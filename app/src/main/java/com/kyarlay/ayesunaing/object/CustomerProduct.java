package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CustomerProduct extends UniversalPost implements Parcelable {


    @SerializedName("product_id")
    private int id;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("option")
    private String option;

    @SerializedName("original_price")
    private int originalPrice;

    @SerializedName("discounted_price")
    private int discountedPrice;

    @SerializedName("point_usage")
    private int pointUsage;

    @SerializedName("image")
    private String image;

    @SerializedName("title")
    private String title;

    public CustomerProduct(){}

    protected CustomerProduct(Parcel in) {
        id = in.readInt();
        quantity = in.readInt();
        option = in.readString();
        originalPrice = in.readInt();
        discountedPrice = in.readInt();
        pointUsage = in.readInt();
        image = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeInt(quantity);
        dest.writeString(option);
        dest.writeDouble(originalPrice);
        dest.writeDouble(discountedPrice);
        dest.writeInt(pointUsage);
        dest.writeString(image);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomerProduct> CREATOR = new Creator<CustomerProduct>() {
        @Override
        public CustomerProduct createFromParcel(Parcel in) {
            return new CustomerProduct(in);
        }

        @Override
        public CustomerProduct[] newArray(int size) {
            return new CustomerProduct[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getPointUsage() {
        return pointUsage;
    }

    public void setPointUsage(int pointUsage) {
        this.pointUsage = pointUsage;
    }
}

/*
"image": "https://res.cloudinary.com/tech-myanmar/image/upload/c_scale,g_center,h_360,q_auto,w_360/almdzoqxcfjxgbtmbboc.jpg",
			"product_id": 1011,
			"quantity": 10,
			"option": "red",
			"original_price": 4800.0,
			"discounted_price": 0,
			"point_usage": 0
 */

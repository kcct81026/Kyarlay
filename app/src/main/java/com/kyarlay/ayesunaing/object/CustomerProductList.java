package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomerProductList extends UniversalPost implements Parcelable {

    @SerializedName("products")
    List<CustomerProduct> prouduts = new ArrayList<>();

    @SerializedName("carts")
    ProductCuromerInfo productCuromerInfo;

    @SerializedName("gifts")
    String[] gifts;

    public CustomerProductList() {
    }

    protected CustomerProductList(Parcel in) {
        prouduts = in.createTypedArrayList(CustomerProduct.CREATOR);
        productCuromerInfo = in.readParcelable(ProductCuromerInfo.class.getClassLoader());
        gifts = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(prouduts);
        dest.writeParcelable(productCuromerInfo, flags);
        dest.writeStringArray(gifts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomerProductList> CREATOR = new Creator<CustomerProductList>() {
        @Override
        public CustomerProductList createFromParcel(Parcel in) {
            return new CustomerProductList(in);
        }

        @Override
        public CustomerProductList[] newArray(int size) {
            return new CustomerProductList[size];
        }
    };

    public List<CustomerProduct> getProuduts() {
        return prouduts;
    }

    public void setProuduts(List<CustomerProduct> prouduts) {
        this.prouduts = prouduts;
    }

    public ProductCuromerInfo getProductCuromerInfo() {
        return productCuromerInfo;
    }

    public void setProductCuromerInfo(ProductCuromerInfo productCuromerInfo) {
        this.productCuromerInfo = productCuromerInfo;
    }

    public String[] getGifts() {
        return gifts;
    }

    public void setGifts(String[] gifts) {
        this.gifts = gifts;
    }
}

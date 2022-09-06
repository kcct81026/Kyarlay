package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesu on 5/3/17.
 */

public class Discount extends UniversalPost{

    int id, productId;

    @SerializedName("discount_id")
    int discount_id;

    @SerializedName("campaign_info")
    String campaign_info;

    @SerializedName("benefit_type")
    String benefit_type;

    @SerializedName("type")
    String discountType;

    @SerializedName("percentage")
    int percentage;

    @SerializedName("num_extra")
    int num_extra;

    @SerializedName("gift_img_url")
    String gift_img_url;

    @SerializedName("gift_info")
    String gift_info;

    @SerializedName("min_count")
    int min_count;

    @SerializedName("max_count")
    int max_count;

    @SerializedName("gift_img_dimen")
    int dimen;

    @SerializedName("count_type")
    String count_type;
    @SerializedName("member_only")
    int member_only;

    @SerializedName("start_at")
    String start_at;

    @SerializedName("end_at")
    String end_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(int discount_id) {
        this.discount_id = discount_id;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getNum_extra() {
        return num_extra;
    }

    public void setNum_extra(int num_extra) {
        this.num_extra = num_extra;
    }

    public String getGift_img_url() {
        return gift_img_url;
    }

    public void setGift_img_url(String gift_img_url) {
        this.gift_img_url = gift_img_url;
    }

    public String getGift_info() {
        return gift_info;
    }

    public void setGift_info(String gift_info) {
        this.gift_info = gift_info;
    }

    public int getMin_count() {
        return min_count;
    }

    public void setMin_count(int min_count) {
        this.min_count = min_count;
    }

    public int getMax_count() {
        return max_count;
    }

    public void setMax_count(int max_count) {
        this.max_count = max_count;
    }

    public String getCount_type() {
        return count_type;
    }

    public void setCount_type(String count_type) {
        this.count_type = count_type;
    }

    public String getCampaign_info() {
        return campaign_info;
    }

    public void setCampaign_info(String campaign_info) {
        this.campaign_info = campaign_info;
    }

    public String getBenefit_type() {
        return benefit_type;
    }

    public void setBenefit_type(String benefit_type) {
        this.benefit_type = benefit_type;
    }

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
    }

    public int getMember_only() {
        return member_only;
    }

    public void setMember_only(int member_only) {
        this.member_only = member_only;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public Discount() {
    }


    public static final Parcelable.Creator<Discount> CREATOR = new Parcelable.Creator<Discount>() {
        public Discount createFromParcel(Parcel in) {
            return new Discount(in);
        }

        public Discount[] newArray(int size) {

            return new Discount[size];
        }

    };

    public Discount(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        id              = in.readInt();
        productId       = in.readInt();
        discount_id     = in.readInt();
        discountType    = in.readString();
        percentage      = in.readInt();
        num_extra       = in.readInt();
        gift_img_url    = in.readString();
        gift_info       = in.readString();
        min_count       = in.readInt();
        max_count       = in.readInt();
        dimen           = in.readInt();
        count_type      = in.readString();
        campaign_info   = in.readString();
        benefit_type    = in.readString();
        member_only     = in.readInt();
        start_at        = in.readString();
        end_at          = in.readString();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(productId);
        dest.writeInt(discount_id);
        dest.writeString(discountType);
        dest.writeInt(percentage);
        dest.writeInt(num_extra);
        dest.writeString(gift_img_url);
        dest.writeString(gift_info);
        dest.writeInt(min_count);
        dest.writeInt(max_count);
        dest.writeInt(dimen);
        dest.writeString(count_type);
        dest.writeString(campaign_info);
        dest.writeString(benefit_type);
        dest.writeInt(member_only);
        dest.writeString(start_at);
        dest.writeString(end_at);
    }
    @Override
    public int describeContents() {
        return 0;
    }


}

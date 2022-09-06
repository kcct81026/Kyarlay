package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MemberBenefitObject extends UniversalPost implements Parcelable {

    @SerializedName("status")
    private int status;
    @SerializedName("total_points")
    private int total_points;
    @SerializedName("expired_points")
    private int expired_points;
    @SerializedName("expired_at")
    private String expired_at;
    @SerializedName("title")
    private String title;
    @SerializedName("current_amt")
    private int current_amt;
    @SerializedName("description")
    private String description;
    @SerializedName("expired_month")
    private int expired_month;
    @SerializedName("member_title")
    private String member_title;
    @SerializedName("member_expired_month")
    private int member_expired_month;
    @SerializedName("member_target_amt")
    private int member_target_amt;
    @SerializedName("benefit_title")
    private String benefit_title;
    @SerializedName("member_description")
    private String member_description;
    @SerializedName("benefit_info")
    private ArrayList<BenefitObject> benefitDataList = new ArrayList<>();

    public MemberBenefitObject(){}

    protected MemberBenefitObject(Parcel in) {
        status = in.readInt();
        total_points = in.readInt();
        expired_points = in.readInt();
        expired_at = in.readString();
        title = in.readString();
        current_amt = in.readInt();
        description = in.readString();
        expired_month = in.readInt();
        member_title = in.readString();
        member_expired_month = in.readInt();
        member_target_amt = in.readInt();
        benefit_title = in.readString();
        member_description = in.readString();
        benefitDataList = in.createTypedArrayList(BenefitObject.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(status);
        dest.writeInt(total_points);
        dest.writeInt(expired_points);
        dest.writeString(expired_at);
        dest.writeString(title);
        dest.writeInt(current_amt);
        dest.writeString(description);
        dest.writeInt(expired_month);
        dest.writeString(member_title);
        dest.writeInt(member_expired_month);
        dest.writeInt(member_target_amt);
        dest.writeString(benefit_title);
        dest.writeString(member_description);
        dest.writeTypedList(benefitDataList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MemberBenefitObject> CREATOR = new Creator<MemberBenefitObject>() {
        @Override
        public MemberBenefitObject createFromParcel(Parcel in) {
            return new MemberBenefitObject(in);
        }

        @Override
        public MemberBenefitObject[] newArray(int size) {
            return new MemberBenefitObject[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal_points() {
        return total_points;
    }

    public void setTotal_points(int total_points) {
        this.total_points = total_points;
    }

    public int getExpired_points() {
        return expired_points;
    }

    public void setExpired_points(int expired_points) {
        this.expired_points = expired_points;
    }

    public String getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(String expired_at) {
        this.expired_at = expired_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCurrent_amt() {
        return current_amt;
    }

    public void setCurrent_amt(int current_amt) {
        this.current_amt = current_amt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExpired_month() {
        return expired_month;
    }

    public void setExpired_month(int expired_month) {
        this.expired_month = expired_month;
    }

    public String getMember_title() {
        return member_title;
    }

    public void setMember_title(String member_title) {
        this.member_title = member_title;
    }

    public int getMember_expired_month() {
        return member_expired_month;
    }

    public void setMember_expired_month(int member_expired_month) {
        this.member_expired_month = member_expired_month;
    }

    public int getMember_target_amt() {
        return member_target_amt;
    }

    public void setMember_target_amt(int member_target_amt) {
        this.member_target_amt = member_target_amt;
    }

    public String getBenefit_title() {
        return benefit_title;
    }

    public void setBenefit_title(String benefit_title) {
        this.benefit_title = benefit_title;
    }

    public String getMember_description() {
        return member_description;
    }

    public void setMember_description(String member_description) {
        this.member_description = member_description;
    }

    public ArrayList<BenefitObject> getBenefitDataList() {
        return benefitDataList;
    }

    public void setBenefitDataList(ArrayList<BenefitObject> benefitDataList) {
        this.benefitDataList = benefitDataList;
    }
}

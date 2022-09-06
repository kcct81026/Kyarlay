package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayesu on 3/18/18.
 */

public class Order extends  UniversalPost implements Parcelable {

    @SerializedName("order_id")
    int order_id;
    @SerializedName("post_type")
    String post_type;
    @SerializedName("voucher_number")
    int voucher_number;
    @SerializedName("ordered_at")
    String ordered_at;
    @SerializedName("total_price")
    int total_price;
    @SerializedName("status")
    String status;

    @SerializedName("feedback_point")
    private int feedback_point;

    @SerializedName("feedback_text")
    private String feedback_text;

    @SerializedName("point")
    private int point;

    @SerializedName("delivery_fee")
    private int delivery_fee;

    @SerializedName("delivery_person")
    private String delivery_person;

    @SerializedName("delivery_phone")
    private String delivery_phone;

    @SerializedName("delivery_date")
    private String delivery_date;

    @SerializedName("delivery_slot")
    private String delivery_slot;

    @SerializedName("address")
    private String address;

    @SerializedName("completed_at")
    private String completed_at;

    @SerializedName("photo_url")
    private String photo_url;

    @SerializedName("dimen")
    private  int dimen;

    @SerializedName("payment_method")
    private String payment_method;

    @SerializedName("payment_status")
    private String payment_status;

    @SerializedName("payment_name")
    private String payment_name;

    @SerializedName("payment_fee")
    private float payment_fee;

    @SerializedName("payment_icon")
    private String payment_icon;

    @SerializedName("remark")
    private String remark;

    @SerializedName("member_discount")
    private int member_discount;

    @SerializedName("item_count")
    private int item_count;

    @SerializedName("acc_name")
    private String acc_name;

    @SerializedName("acc_num")
    private String acc_num;

    @SerializedName("discounts")
    List<OrderDiscount> orderDiscountList = new ArrayList<>();

    private int pendingCount ;

    public Order(){}

    protected Order(Parcel in) {
        super();
        order_id = in.readInt();
        post_type = in.readString();
        voucher_number = in.readInt();
        ordered_at = in.readString();
        total_price = in.readInt();
        status = in.readString();
        feedback_point = in.readInt();
        feedback_text = in.readString();
        point = in.readInt();
        delivery_fee = in.readInt();
        delivery_person = in.readString();
        delivery_phone = in.readString();
        delivery_date = in.readString();
        delivery_slot = in.readString();
        address = in.readString();
        completed_at = in.readString();
        photo_url = in.readString();
        dimen = in.readInt();
        payment_method = in.readString();
        payment_status = in.readString();
        payment_name = in.readString();
        payment_fee = in.readFloat();
        payment_icon = in.readString();
        remark = in.readString();
        member_discount = in.readInt();
        item_count = in.readInt();
        orderDiscountList = in.createTypedArrayList(OrderDiscount.CREATOR);
        pendingCount = in.readInt();
        acc_name = in.readString();
        acc_num = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(order_id);
        dest.writeString(post_type);
        dest.writeInt(voucher_number);
        dest.writeString(ordered_at);
        dest.writeInt(total_price);
        dest.writeString(status);
        dest.writeInt(feedback_point);
        dest.writeString(feedback_text);
        dest.writeInt(point);
        dest.writeInt(delivery_fee);
        dest.writeString(delivery_person);
        dest.writeString(delivery_phone);
        dest.writeString(delivery_date);
        dest.writeString(delivery_slot);
        dest.writeString(address);
        dest.writeString(completed_at);
        dest.writeString(photo_url);
        dest.writeInt(dimen);
        dest.writeString(payment_method);
        dest.writeString(payment_status);
        dest.writeString(payment_name);
        dest.writeFloat(payment_fee);
        dest.writeString(payment_icon);
        dest.writeString(remark);
        dest.writeInt(member_discount);
        dest.writeInt(item_count);
        dest.writeTypedList(orderDiscountList);
        dest.writeInt(pendingCount);
        dest.writeString(acc_name);
        dest.writeString(acc_num);
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public String getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(String acc_num) {
        this.acc_num = acc_num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public int getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public int getVoucher_number() {
        return voucher_number;
    }

    public void setVoucher_number(int voucher_number) {
        this.voucher_number = voucher_number;
    }

    public String getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(String ordered_at) {
        this.ordered_at = ordered_at;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFeedback_point() {
        return feedback_point;
    }

    public void setFeedback_point(int feedback_point) {
        this.feedback_point = feedback_point;
    }

    public String getFeedback_text() {
        return feedback_text;
    }

    public void setFeedback_text(String feedback_text) {
        this.feedback_text = feedback_text;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(int delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getDelivery_person() {
        return delivery_person;
    }

    public void setDelivery_person(String delivery_person) {
        this.delivery_person = delivery_person;
    }

    public String getDelivery_phone() {
        return delivery_phone;
    }

    public void setDelivery_phone(String delivery_phone) {
        this.delivery_phone = delivery_phone;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_slot() {
        return delivery_slot;
    }

    public void setDelivery_slot(String delivery_slot) {
        this.delivery_slot = delivery_slot;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(String completed_at) {
        this.completed_at = completed_at;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public float getPayment_fee() {
        return payment_fee;
    }

    public void setPayment_fee(float payment_fee) {
        this.payment_fee = payment_fee;
    }

    public String getPayment_icon() {
        return payment_icon;
    }

    public void setPayment_icon(String payment_icon) {
        this.payment_icon = payment_icon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getMember_discount() {
        return member_discount;
    }

    public void setMember_discount(int member_discount) {
        this.member_discount = member_discount;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public List<OrderDiscount> getOrderDiscountList() {
        return orderDiscountList;
    }

    public void setOrderDiscountList(List<OrderDiscount> orderDiscountList) {
        this.orderDiscountList = orderDiscountList;
    }
}

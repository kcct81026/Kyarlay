package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;
import com.kyarlay.ayesunaing.activity.InviteFriendActivity;

public class InviteHistory extends UniversalPost {

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("status")
    private String status;

    public InviteHistory(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

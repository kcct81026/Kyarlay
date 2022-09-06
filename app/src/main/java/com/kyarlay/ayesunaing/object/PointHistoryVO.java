package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

public class PointHistoryVO extends  UniversalPost{

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("expired_at")
    private String expiredAt;

    @SerializedName("usage_type")
    private String type;

    @SerializedName("point")
    private int point;

    public PointHistoryVO(){}

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}

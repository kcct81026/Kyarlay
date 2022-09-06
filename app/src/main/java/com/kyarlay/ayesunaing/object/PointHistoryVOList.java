package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PointHistoryVOList extends UniversalPost{
    @SerializedName("month_year")
    private String month_year;

    @SerializedName("data")
    List<PointHistoryVO> pointHistoryVOList = new ArrayList<>();

    public  PointHistoryVOList(){}

    public String getMonth_year() {
        return month_year;
    }

    public void setMonth_year(String month_year) {
        this.month_year = month_year;
    }

    public List<PointHistoryVO> getPointHistoryVOList() {
        return pointHistoryVOList;
    }

    public void setPointHistoryVOList(List<PointHistoryVO> pointHistoryVOList) {
        this.pointHistoryVOList = pointHistoryVOList;
    }
}

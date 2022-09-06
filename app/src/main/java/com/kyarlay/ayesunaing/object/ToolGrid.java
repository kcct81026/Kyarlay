package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ToolGrid extends UniversalPost {

    private List<ToolChildObject> toolObjectArrayList = new ArrayList<>();

    public ToolGrid(){}

    public List<ToolChildObject> getToolObjectArrayList() {
        return toolObjectArrayList;
    }

    public void setToolObjectArrayList(List<ToolChildObject> toolObjectArrayList) {
        this.toolObjectArrayList = toolObjectArrayList;
    }

    public static final Parcelable.Creator<ToolGrid> CREATOR = new Parcelable.Creator<ToolGrid>() {
        public ToolGrid createFromParcel(Parcel in) {
            return new ToolGrid(in);
        }

        public ToolGrid[] newArray(int size) {

            return new ToolGrid[size];
        }

    };

    public ToolGrid(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {

        in.readTypedList(toolObjectArrayList, ToolChildObject.CREATOR);

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeTypedList(toolObjectArrayList);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}

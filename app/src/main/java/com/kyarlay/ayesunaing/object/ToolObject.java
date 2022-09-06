package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ToolObject extends UniversalPost{

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("tag")
    private String tag;

    @SerializedName("image")
    private String image;

    @SerializedName("tool_type")
    private String tool_type;

    @SerializedName("tool_url")
    private String tool_url;

    @SerializedName("child_categories")
    private List<ToolChildObject> toolChildObjects = new ArrayList<>();

    public ToolObject(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTool_type() {
        return tool_type;
    }

    public void setTool_type(String tool_type) {
        this.tool_type = tool_type;
    }

    public String getTool_url() {
        return tool_url;
    }

    public void setTool_url(String tool_url) {
        this.tool_url = tool_url;
    }

    public List<ToolChildObject> getToolChildObjects() {
        return toolChildObjects;
    }

    public void setToolChildObjects(List<ToolChildObject> toolChildObjects) {
        this.toolChildObjects = toolChildObjects;
    }

    public static final Parcelable.Creator<ToolObject> CREATOR = new Parcelable.Creator<ToolObject>() {
        public ToolObject createFromParcel(Parcel in) {
            return new ToolObject(in);
        }

        public ToolObject[] newArray(int size) {

            return new ToolObject[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(tag);
        dest.writeString(image);
        dest.writeString(tool_type);
        dest.writeString(tool_url);
        dest.writeTypedList(toolChildObjects);
    }

    public ToolObject(Parcel in) {
        super();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id         = in.readInt();
        name       = in.readString();
        tag       = in.readString();
        image      = in.readString();
        tool_type         = in.readString();
        tool_url         = in.readString();
        in.readTypedList(toolChildObjects, ToolChildObject.CREATOR);


    }
}

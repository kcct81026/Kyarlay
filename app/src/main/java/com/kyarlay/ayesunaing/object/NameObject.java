package com.kyarlay.ayesunaing.object;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

import java.util.jar.Attributes;

public class NameObject extends UniversalPost {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    private boolean saveName;

    public NameObject(){}

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

    public boolean getSaveName() {
        return saveName;
    }

    public void setSaveName(boolean saveName) {
        this.saveName = saveName;
    }
}

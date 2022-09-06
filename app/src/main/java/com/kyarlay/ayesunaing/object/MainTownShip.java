package com.kyarlay.ayesunaing.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainTownShip extends UniversalPost {

    private int prid;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("townships")
    private List<TownShip> townShipArrayList = new ArrayList<>();

    public MainTownShip(int id, String name, List<TownShip> townShipArrayList) {
        this.id = id;
        this.name = name;
        this.townShipArrayList = townShipArrayList;
    }

    public List<TownShip> getTownShipArrayList() {
        return townShipArrayList;
    }

    public void setTownShipArrayList(List<TownShip> townShipArrayList) {
        this.townShipArrayList = townShipArrayList;
    }

    public int getPrid() {
        return prid;
    }

    public void setPrid(int prid) {
        this.prid = prid;
    }

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
}

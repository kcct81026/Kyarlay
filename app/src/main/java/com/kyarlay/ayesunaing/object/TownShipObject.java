package com.kyarlay.ayesunaing.object;

import java.util.ArrayList;
import java.util.List;

public class TownShipObject extends UniversalPost {

    private int prid;

    private List<MainTownShip> townShipList = new ArrayList<>();
    private List<Addresses> addressesList = new ArrayList<>();

    public int getPrid() {
        return prid;
    }

    public void setPrid(int prid) {
        this.prid = prid;
    }

    public List<MainTownShip> getTownShipList() {
        return townShipList;
    }

    public void setTownShipList(List<MainTownShip> townShipList) {
        this.townShipList = townShipList;
    }

    public List<Addresses> getAddressesList() {
        return addressesList;
    }

    public void setAddressesList(List<Addresses> addressesList) {
        this.addressesList = addressesList;
    }
}

package com.kyarlay.ayesunaing.object;


import java.util.ArrayList;
import java.util.List;

public class GiftObjectList extends UniversalPost {

    private String title;
    private List<GiftObject> items = new ArrayList<>();

    public GiftObjectList(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GiftObject> getItems() {
        return items;
    }

    public void setItems(List<GiftObject> items) {
        this.items = items;
    }
}

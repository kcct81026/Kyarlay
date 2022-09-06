package com.kyarlay.ayesunaing.object;

import java.util.ArrayList;
import java.util.List;

public class FlashList extends UniversalPost {

    private String title;

    private List<Product> productArrayList = new ArrayList<>();

    public FlashList(){}

    public List<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(List<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

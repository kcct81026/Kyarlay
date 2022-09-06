package com.kyarlay.ayesunaing.object;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ParcelCreator")
public class OrderDetailsObj extends UniversalPost {

    private Order order;
    private String title;
    private String url;
    private List<MainNav> mainNavList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private boolean viewAll;

    public List<MainNav> getMainNavList() {
        return mainNavList;
    }

    public void setMainNavList(List<MainNav> mainNavList) {
        this.mainNavList = mainNavList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isViewAll() {
        return viewAll;
    }

    public void setViewAll(boolean viewAll) {
        this.viewAll = viewAll;
    }

    public OrderDetailsObj(){}

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}

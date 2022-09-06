package com.kyarlay.ayesunaing.object;

import java.util.ArrayList;
import java.util.List;

public class MainPaymentObj extends UniversalPost {

    private int id;

    private List<PaymentObject> paymentObjectList = new ArrayList<>();

    public MainPaymentObj(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PaymentObject> getPaymentObjectList() {
        return paymentObjectList;
    }

    public void setPaymentObjectList(List<PaymentObject> paymentObjectList) {
        this.paymentObjectList = paymentObjectList;
    }
}

package com.kyarlay.ayesunaing.object;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ChkLstMainObject extends UniversalPost implements Parcelable {

    int id;
    String title;
    List<ChkLstItem> chklstItemList = new ArrayList<>();
    int totalQty;
    int checkQty;



}

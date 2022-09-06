package com.kyarlay.ayesunaing.object;

import java.util.ArrayList;
import java.util.List;

public class MainMallGrid extends  UniversalPost{
    private List<MallGridObject> mallGridObjectList = new ArrayList<>();

    public List<MallGridObject> getMallGridObjectList() {
        return mallGridObjectList;
    }

    public void setMallGridObjectList(List<MallGridObject> mallGridObjectList) {
        this.mallGridObjectList = mallGridObjectList;
    }
}

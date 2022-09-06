package com.kyarlay.ayesunaing.object;

/**
 * Created by aye on 6/8/15.
 */
public class MomolayAds {
    int id, dimen;
    String title,url, desc,media_url,media_foramt;

    public MomolayAds() {
    }

    public MomolayAds(int id, int dimen, String title, String url, String desc, String media_url, String media_foramt) {
        this.id = id;
        this.dimen = dimen;
        this.title = title;
        this.url = url;
        this.desc = desc;
        this.media_url = media_url;
        this.media_foramt = media_foramt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDimen() {
        return dimen;
    }

    public void setDimen(int dimen) {
        this.dimen = dimen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_foramt() {
        return media_foramt;
    }

    public void setMedia_foramt(String media_foramt) {
        this.media_foramt = media_foramt;
    }
}

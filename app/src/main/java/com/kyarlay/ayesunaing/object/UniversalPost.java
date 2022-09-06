package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class UniversalPost implements Parcelable {
    protected String postType;

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}

package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class Supplier extends UniversalPost {
    int priId;

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    public Supplier() {
    }

    public Supplier(int id, int priId, String name) {
        this.id = id;
        this.priId = priId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriId() {
        return priId;
    }

    public void setPriId(int priId) {
        this.priId = priId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final Parcelable.Creator<Supplier> CREATOR = new Parcelable.Creator<Supplier>() {
        public Supplier createFromParcel(Parcel in) {
            return new Supplier(in);
        }

        public Supplier[] newArray(int size) {

            return new Supplier[size];
        }

    };

    public Supplier(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        id    = in.readInt();
        priId = in.readInt();
        name  = in.readString();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(priId);
        dest.writeString(name);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}

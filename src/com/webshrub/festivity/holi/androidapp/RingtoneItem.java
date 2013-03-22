package com.webshrub.festivity.holi.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:14 PM
 */
public class RingtoneItem implements FestivityItem {
    private int id;
    private String name;
    private String nameWithExtension;
    private String assetUri;

    public RingtoneItem() {
    }

    public RingtoneItem(int id, String name, String nameWithExtension, String assetUri) {
        this.id = id;
        this.name = name;
        this.nameWithExtension = nameWithExtension;
        this.assetUri = assetUri;
    }

    public RingtoneItem(Parcel in) {
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameWithExtension() {
        return nameWithExtension;
    }

    public void setNameWithExtension(String nameWithExtension) {
        this.nameWithExtension = nameWithExtension;
    }

    public String getAssetUri() {
        return assetUri;
    }

    public void setAssetUri(String assetUri) {
        this.assetUri = assetUri;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(nameWithExtension);
        dest.writeString(assetUri);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        nameWithExtension = in.readString();
        assetUri = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public RingtoneItem createFromParcel(Parcel in) {
            return new RingtoneItem(in);
        }

        public RingtoneItem[] newArray(int size) {
            return new RingtoneItem[size];
        }
    };
}

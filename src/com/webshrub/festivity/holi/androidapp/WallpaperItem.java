package com.webshrub.festivity.holi.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:14 PM
 */
public class WallpaperItem implements FestivityItem {
    private int id;
    private String name;
    private String assetUri;
    private String displayAssetUri;

    public WallpaperItem() {
    }

    public WallpaperItem(int id, String name, String assetUri, String displayAssetUri) {
        this.id = id;
        this.name = name;
        this.assetUri = assetUri;
        this.displayAssetUri = displayAssetUri;
    }

    public WallpaperItem(Parcel in) {
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

    public String getAssetUri() {
        return assetUri;
    }

    public void setAssetUri(String assetUri) {
        this.assetUri = assetUri;
    }

    public String getDisplayAssetUri() {
        return displayAssetUri;
    }

    public void setDisplayAssetUri(String displayAssetUri) {
        this.displayAssetUri = displayAssetUri;
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
        dest.writeString(assetUri);
        dest.writeString(displayAssetUri);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        assetUri = in.readString();
        displayAssetUri = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public WallpaperItem createFromParcel(Parcel in) {
            return new WallpaperItem(in);
        }

        public WallpaperItem[] newArray(int size) {
            return new WallpaperItem[size];
        }
    };
}

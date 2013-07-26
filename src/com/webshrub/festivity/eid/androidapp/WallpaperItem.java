package com.webshrub.festivity.eid.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:14 PM
 */
public class WallpaperItem extends FestivityItem {
    private String displayAssetUri;

    public WallpaperItem() {
        super();
    }

    public WallpaperItem(int id, String name, String assetUri, String displayAssetUri) {
        super(id, name, assetUri);
        this.displayAssetUri = displayAssetUri;
    }

    public WallpaperItem(Parcel in) {
        readFromParcel(in);
    }

    public String getDisplayAssetUri() {
        return displayAssetUri;
    }

    public void setDisplayAssetUri(String displayAssetUri) {
        this.displayAssetUri = displayAssetUri;
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

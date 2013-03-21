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
    private String teaser;
    private String details;
    private String assetUri;

    public WallpaperItem() {
    }

    public WallpaperItem(int id, String teaser, String details, String assetUri) {
        this.id = id;
        this.teaser = teaser;
        this.details = details;
        this.assetUri = assetUri;
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

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAssetUri() {
        return assetUri;
    }

    public void setAssetUri(String assetUri) {
        this.assetUri = assetUri;
    }

    @Override
    public String toString() {
        return teaser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(teaser);
        dest.writeString(details);
        dest.writeString(assetUri);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        teaser = in.readString();
        details = in.readString();
        assetUri = in.readString();
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

package com.webshrub.festivity.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:14 PM
 */
public class WallpaperItem implements FestivityItem{
    private String id;
    private String wallpaperTeaser;
    private String wallpaperText;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getTeaser() {
        return null;
    }

    @Override
    public String getDetails() {
        return null;
    }
}

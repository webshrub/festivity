package com.webshrub.festivity.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:15 PM
 */
public class Ringtone implements Parcelable{
    private String id;
    private String ringtoneTeaser;
    private String ringtoneText;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}

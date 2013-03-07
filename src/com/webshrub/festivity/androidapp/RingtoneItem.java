package com.webshrub.festivity.androidapp;

import android.os.Parcel;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:15 PM
 */
public class RingtoneItem implements FestivityItem {
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

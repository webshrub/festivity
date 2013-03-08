package com.webshrub.festivity.holi.androidapp;

import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 1:21 PM
 */
public interface FestivityItem extends Parcelable {
    public int getId();

    public String getTeaser();

    public String getDetails();
}

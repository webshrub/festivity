package com.webshrub.festivity.eid.androidapp;

import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 1:21 PM
 */
public abstract class FestivityItem implements Parcelable {
    protected int id;
    protected String name;
    protected String assetUri;

    public FestivityItem() {
    }

    public FestivityItem(int id, String name, String assetUri) {
        this.id = id;
        this.name = name;
        this.assetUri = assetUri;
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

    @Override
    public String toString() {
        return name;
    }
}

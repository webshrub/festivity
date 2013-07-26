package com.webshrub.festivity.eid.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:14 PM
 */
public class RingtoneItem extends FestivityItem {
    private String nameWithExtension;

    public RingtoneItem() {
        super();
    }

    public RingtoneItem(int id, String name, String nameWithExtension, String assetUri) {
        super(id, name, assetUri);
        this.nameWithExtension = nameWithExtension;
    }

    public RingtoneItem(Parcel in) {
        readFromParcel(in);
    }

    public String getNameWithExtension() {
        return nameWithExtension;
    }

    public void setNameWithExtension(String nameWithExtension) {
        this.nameWithExtension = nameWithExtension;
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

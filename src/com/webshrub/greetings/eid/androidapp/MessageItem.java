package com.webshrub.greetings.eid.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:14 PM
 */
public class MessageItem extends FestivityItem {

    public MessageItem() {
        super();
    }

    public MessageItem(int id, String name, String assetUri) {
        super(id, name, assetUri);
    }

    public MessageItem(Parcel in) {
        readFromParcel(in);
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
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        assetUri = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MessageItem createFromParcel(Parcel in) {
            return new MessageItem(in);
        }

        public MessageItem[] newArray(int size) {
            return new MessageItem[size];
        }
    };
}

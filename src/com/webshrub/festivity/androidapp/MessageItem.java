package com.webshrub.festivity.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:14 PM
 */
public class MessageItem implements FestivityItem {
    private int id;
    private String teaser;
    private String details;

    public MessageItem() {
    }

    public MessageItem(int id, String teaser, String details) {
        this.id = id;
        this.teaser = teaser;
        this.details = details;
    }

    public MessageItem(Parcel in) {
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
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        teaser = in.readString();
        details = in.readString();
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
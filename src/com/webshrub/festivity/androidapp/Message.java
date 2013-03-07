package com.webshrub.festivity.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:14 PM
 */
public class Message implements Parcelable {
    private int id;
    private String messageTeaser;
    private String messageText;

    public Message() {
    }

    public Message(int id, String messageTeaser, String messageText) {
        this.id = id;
        this.messageTeaser = messageTeaser;
        this.messageText = messageText;
    }

    public Message(Parcel in) {
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageTeaser() {
        return messageTeaser;
    }

    public void setMessageTeaser(String messageTeaser) {
        this.messageTeaser = messageTeaser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return messageTeaser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(messageTeaser);
        dest.writeString(messageText);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        messageTeaser = in.readString();
        messageText = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}

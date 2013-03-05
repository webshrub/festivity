package com.webshrub.festivity.androidapp;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 3:14 PM
 */
public class Message {
    private String id;
    private String messageTeaser;
    private String messageText;

    public Message(String id, String messageTeaser, String messageText) {
        this.id = id;
        this.messageTeaser = messageTeaser;
        this.messageText = messageText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}

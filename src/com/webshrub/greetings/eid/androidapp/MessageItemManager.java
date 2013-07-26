package com.webshrub.greetings.eid.androidapp;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageItemManager {
    private Context context;
    private List<MessageItem> messageItemList;

    public MessageItemManager(Context context) {
        try {
            this.context = context;
            AssetManager assetManager = context.getAssets();
            String[] messageNames = assetManager.list(FestivityConstants.MESSAGE_ASSETS_DIR);
            messageItemList = new ArrayList<MessageItem>(messageNames.length);
            for (int i = 0; i < messageNames.length; i++) {
                String name = FestivityUtility.stripExtension(messageNames[i]);
                String assetUri = FestivityConstants.MESSAGE_ASSETS_DIR + "/" + messageNames[i];
                messageItemList.add(new MessageItem(i, name, assetUri));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MessageItem> getMessageItemList() {
        return messageItemList;
    }

    public String getMessageString(MessageItem messageItem) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(messageItem.getAssetUri());
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MessageItem getMessageItemAt(int position) {
        return messageItemList.get(position);
    }
}

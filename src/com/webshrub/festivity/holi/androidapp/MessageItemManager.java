package com.webshrub.festivity.holi.androidapp;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageItemManager {
    private List<MessageItem> messageItemList;

    public MessageItemManager(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            String[] messageNames = assetManager.list(FestivityConstants.MESSAGE_DIR);
            messageItemList = new ArrayList<MessageItem>(messageNames.length);
            for (int i = 0; i < messageNames.length; i++) {
                String messageName = messageNames[i].substring(0, messageNames[i].length() - 4);
                String messageUri = FestivityConstants.MESSAGE_DIR + "/" + messageNames[i];
                messageItemList.add(new MessageItem(i, messageName, messageUri));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MessageItem> getMessageItemList() {
        return messageItemList;
    }
}

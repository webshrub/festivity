package com.webshrub.festivity.holi.androidapp;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RingtoneItemManager {
    private List<RingtoneItem> ringtoneItemList;

    public RingtoneItemManager(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            String[] ringtoneNames = assetManager.list(FestivityConstants.RINGTONE_DIR);
            ringtoneItemList = new ArrayList<RingtoneItem>(ringtoneNames.length);
            for (int i = 0; i < ringtoneNames.length; i++) {
                String ringtoneName = ringtoneNames[i].substring(0, ringtoneNames[i].length() - 4);
                String ringtoneUri = FestivityConstants.RINGTONE_DIR + "/" + ringtoneNames[i];
                ringtoneItemList.add(new RingtoneItem(i, ringtoneName, ringtoneUri));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RingtoneItem> getRingtoneItemList() {
        return ringtoneItemList;
    }

    public RingtoneItem getNextRingtoneItem(RingtoneItem currentRingtoneItem) {
        if (!(currentRingtoneItem.getId() == ringtoneItemList.size() - 1)) {
            return ringtoneItemList.get(currentRingtoneItem.getId() + 1);
        } else {
            return ringtoneItemList.get(0);
        }
    }


    public RingtoneItem getPreviousRingtoneItem(RingtoneItem currentRingtoneItem) {
        if (!(currentRingtoneItem.getId() == 0)) {
            return ringtoneItemList.get(currentRingtoneItem.getId() - 1);
        } else {
            return ringtoneItemList.get(ringtoneItemList.size() - 1);
        }
    }

    public RingtoneItem getRandomRingtoneItem() {
        int songIndex = new Random().nextInt((ringtoneItemList.size() - 1) + 1);
        return ringtoneItemList.get(songIndex);
    }
}

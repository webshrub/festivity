package com.webshrub.festivity.holi.androidapp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RingtoneItemManager {
    private static final RingtoneItemManager INSTANCE = new RingtoneItemManager();
    private List<RingtoneItem> ringtoneItemList;

    private RingtoneItemManager() {
        ringtoneItemList = new ArrayList<RingtoneItem>();
        File ringtoneMediaDir = new File(FestivityConstants.RINGTONE_MEDIA_DIR);
        if (ringtoneMediaDir.listFiles(new RingtoneItemExtensionFilter()).length > 0) {
            File[] files = ringtoneMediaDir.listFiles(new RingtoneItemExtensionFilter());
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String ringtoneName = file.getName().substring(0, (file.getName().length() - 4));
                String ringtonePath = file.getPath();
                ringtoneItemList.add(new RingtoneItem(i, ringtoneName, ringtonePath));
            }
        }
    }

    public static RingtoneItemManager getInstance() {
        return INSTANCE;
    }

    public List<RingtoneItem> getRingtoneItemList() {
        return ringtoneItemList;
    }

    public RingtoneItem getNextRingtoneItem(RingtoneItem currentRigRingtoneItem) {
        return ringtoneItemList.get(currentRigRingtoneItem.getId() + 1);
    }

    public RingtoneItem getPreviousRingtoneItem(RingtoneItem currentRigRingtoneItem) {
        return ringtoneItemList.get(currentRigRingtoneItem.getId() - 1);
    }

    public RingtoneItem getFirstRingtoneItem() {
        return ringtoneItemList.get(0);
    }

    public RingtoneItem getLastRingtoneItem() {
        return ringtoneItemList.get(ringtoneItemList.size() - 1);
    }

    public RingtoneItem getRandomRingtoneItem() {
        int songIndex = new Random().nextInt((ringtoneItemList.size() - 1) + 1);
        return ringtoneItemList.get(songIndex);
    }

    public boolean isFirst(RingtoneItem currentRingtoneItem) {
        return currentRingtoneItem.getId() == 0;
    }

    public boolean isLast(RingtoneItem currentRingtoneItem) {
        return currentRingtoneItem.getId() == ringtoneItemList.size() - 1;
    }

    private class RingtoneItemExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}

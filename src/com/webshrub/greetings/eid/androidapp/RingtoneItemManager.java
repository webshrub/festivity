package com.webshrub.greetings.eid.androidapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RingtoneItemManager {
    private Context context;
    private List<RingtoneItem> ringtoneItemList;

    public RingtoneItemManager(Context context) {
        try {
            this.context = context;
            AssetManager assetManager = context.getAssets();
            String[] ringtoneNames = assetManager.list(FestivityConstants.RINGTONE_ASSETS_DIR);
            ringtoneItemList = new ArrayList<RingtoneItem>(ringtoneNames.length);
            for (int i = 0; i < ringtoneNames.length; i++) {
                String name = FestivityUtility.stripExtension(ringtoneNames[i]);
                String nameWithExtension = ringtoneNames[i];
                String assetUri = FestivityConstants.RINGTONE_ASSETS_DIR + "/" + ringtoneNames[i];
                ringtoneItemList.add(new RingtoneItem(i, name, nameWithExtension, assetUri));
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

    public void setRingtone(RingtoneItem currentRingtoneItem) {
        try {
            File copiedRingtoneFile = FestivityUtility.copyFestivityItemToStorage(context, currentRingtoneItem, FestivityConstants.RINGTONE_STORAGE_DIR);

            Uri newRingtoneUri = MediaStore.Audio.Media.getContentUriForPath(copiedRingtoneFile.getAbsolutePath());
            context.getContentResolver().delete(newRingtoneUri, MediaStore.MediaColumns.DATA + "=\"" + copiedRingtoneFile.getAbsolutePath() + "\"", null);

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DATA, copiedRingtoneFile.getAbsolutePath());
            values.put(MediaStore.MediaColumns.TITLE, currentRingtoneItem.getName());
            values.put(MediaStore.MediaColumns.MIME_TYPE, MimeTypeMap.getSingleton().getMimeTypeFromExtension(FestivityUtility.getExtension(currentRingtoneItem.getNameWithExtension())));
            values.put(MediaStore.MediaColumns.SIZE, copiedRingtoneFile.length());
            values.put(MediaStore.Audio.Media.ARTIST, currentRingtoneItem.getName());
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
            values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
            values.put(MediaStore.Audio.Media.IS_ALARM, false);
            values.put(MediaStore.Audio.Media.IS_MUSIC, false);

            Uri uriToInsertValues = MediaStore.Audio.Media.getContentUriForPath(copiedRingtoneFile.getAbsolutePath());
            Uri actualDefaultRingtoneUri = context.getContentResolver().insert(uriToInsertValues, values);

            RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, actualDefaultRingtoneUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

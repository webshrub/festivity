package com.webshrub.festivity.holi.androidapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
                String ringtoneName = ringtoneNames[i].substring(0, ringtoneNames[i].length() - 4);
                String ringtoneUri = FestivityConstants.RINGTONE_ASSETS_DIR + "/" + ringtoneNames[i];
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

    public void setRingtone(RingtoneItem currentRingtoneItem) {
        try {
            File newRingtoneFile = copyRingtoneToStorage(currentRingtoneItem);

            Uri newRingtoneUri = MediaStore.Audio.Media.getContentUriForPath(newRingtoneFile.getAbsolutePath());
            context.getContentResolver().delete(newRingtoneUri, MediaStore.MediaColumns.DATA + "=\"" + newRingtoneFile.getAbsolutePath() + "\"", null);

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DATA, newRingtoneFile.getAbsolutePath());
            values.put(MediaStore.MediaColumns.TITLE, currentRingtoneItem.getTeaser());
            values.put(MediaStore.MediaColumns.MIME_TYPE, FestivityConstants.RINGTONE_MIME_TYPE);
            values.put(MediaStore.MediaColumns.SIZE, newRingtoneFile.length());
            values.put(MediaStore.Audio.Media.ARTIST, currentRingtoneItem.getTeaser());
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
            values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
            values.put(MediaStore.Audio.Media.IS_ALARM, false);
            values.put(MediaStore.Audio.Media.IS_MUSIC, false);

            Uri uriToInsertValues = MediaStore.Audio.Media.getContentUriForPath(newRingtoneFile.getAbsolutePath());
            Uri actualDefaultRingtoneUri = context.getContentResolver().insert(uriToInsertValues, values);

            RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, actualDefaultRingtoneUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File copyRingtoneToStorage(RingtoneItem currentRingtoneItem) throws IOException {
        boolean externalStoragePresent = FestivityUtility.getInstance().isExternalStoragePresent(context);
        if (externalStoragePresent) {
            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/" + FestivityConstants.RINGTONE_STORAGE_DIR);
            folder.mkdirs();
            String fileName = currentRingtoneItem.getTeaser() + FestivityConstants.RINGTONE_EXTENSION;
            File destinationFile = new File(folder, fileName);
            byte[] buffer = new byte[1024];
            AssetFileDescriptor sourceFileDescriptor = context.getAssets().openFd(currentRingtoneItem.getDetails());
            FileInputStream inputStream = sourceFileDescriptor.createInputStream();
            FileOutputStream outputStream = new FileOutputStream(destinationFile);
            int i = inputStream.read(buffer);
            while (i != -1) {
                outputStream.write(buffer, 0, i);
                i = inputStream.read(buffer);
            }
            outputStream.close();
            return destinationFile;
        }
        return null;
    }
}

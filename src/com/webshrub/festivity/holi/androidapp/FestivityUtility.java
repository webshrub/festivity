package com.webshrub.festivity.holi.androidapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/21/13
 * Time: 4:55 PM
 */
public class FestivityUtility {
    private static final FestivityUtility INSTANCE = new FestivityUtility();

    private FestivityUtility() {

    }

    public static FestivityUtility getInstance() {
        return INSTANCE;
    }

    public void setRingtone(Context context, RingtoneItem ringtoneItem) {
        try {
            File newRingtoneFile = copyRingtoneToStorage(context, ringtoneItem);

            Uri newRingtoneUri = MediaStore.Audio.Media.getContentUriForPath(newRingtoneFile.getAbsolutePath());
            context.getContentResolver().delete(newRingtoneUri, MediaStore.MediaColumns.DATA + "=\"" + newRingtoneFile.getAbsolutePath() + "\"", null);

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DATA, newRingtoneFile.getAbsolutePath());
            values.put(MediaStore.MediaColumns.TITLE, ringtoneItem.getTeaser());
            values.put(MediaStore.MediaColumns.MIME_TYPE, FestivityConstants.RINGTONE_MIME_TYPE);
            values.put(MediaStore.MediaColumns.SIZE, newRingtoneFile.length());
            values.put(MediaStore.Audio.Media.ARTIST, ringtoneItem.getTeaser());
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

    public File copyRingtoneToStorage(Context context, RingtoneItem ringtoneItem) throws IOException {
        if (isExternalStoragePresent(context)) {
            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/" + FestivityConstants.RINGTONE_STORAGE_DIR);
            folder.mkdirs();
            String fileName = ringtoneItem.getTeaser() + FestivityConstants.RINGTONE_EXTENSION;
            File destinationFile = new File(folder, fileName);
            byte[] buffer = new byte[1024];
            AssetFileDescriptor sourceFileDescriptor = context.getAssets().openFd(ringtoneItem.getDetails());
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

    public boolean isExternalStoragePresent(Context context) {
        boolean externalStorageAvailable;
        boolean externalStorageWritable;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            externalStorageAvailable = externalStorageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            externalStorageAvailable = true;
            externalStorageWritable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need to know is we can neither read nor write
            externalStorageAvailable = externalStorageWritable = false;
        }
        if (!((externalStorageAvailable) && (externalStorageWritable))) {
            Toast.makeText(context, "SD card not present", Toast.LENGTH_LONG).show();
        }
        return (externalStorageAvailable) && (externalStorageWritable);
    }
}

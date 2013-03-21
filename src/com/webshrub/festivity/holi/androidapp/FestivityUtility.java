package com.webshrub.festivity.holi.androidapp;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

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

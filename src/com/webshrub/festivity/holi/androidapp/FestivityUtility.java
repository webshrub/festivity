package com.webshrub.festivity.holi.androidapp;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/21/13
 * Time: 4:55 PM
 */
public class FestivityUtility {
    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString;
        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage;
        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);
        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;
        // return percentage
        return percentage.intValue();
    }

    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);
        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    public static boolean isExternalStoragePresent(Context context) {
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

    public static String stripExtension(String inputString) {
        int lastIndex = inputString.lastIndexOf(".");
        if (lastIndex < 0) {
            return inputString;
        }
        int fileSeparatorLastIndex = inputString.lastIndexOf(File.separator);
        if (fileSeparatorLastIndex < 0 && lastIndex == 0) {
            return inputString;
        }
        if (fileSeparatorLastIndex >= 0 && fileSeparatorLastIndex > lastIndex) {
            return inputString;
        }
        return inputString.substring(0, lastIndex);
    }

    public static String getExtension(String inputString) {
        return inputString.substring(inputString.lastIndexOf("."));
    }
}

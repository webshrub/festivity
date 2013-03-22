package com.webshrub.festivity.holi.androidapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/22/13
 * Time: 12:43 PM
 */
public class FestivityContentProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws FileNotFoundException {
        AssetManager assetManager = getContext().getAssets();
        List<String> pathSegments = uri.getPathSegments();
        String assetFolder = pathSegments.get(0);
        String fileName = pathSegments.get(1);
        if (assetFolder.equalsIgnoreCase(FestivityConstants.RINGTONE_ASSETS_DIR)) {
            fileName = FestivityConstants.RINGTONE_ASSETS_DIR + "/" + uri.getLastPathSegment();
        } else if (assetFolder.equalsIgnoreCase(FestivityConstants.WALLPAPER_ASSETS_DIR)) {
            fileName = FestivityConstants.WALLPAPER_ASSETS_DIR + "/" + uri.getLastPathSegment();
        }
        AssetFileDescriptor assetFileDescriptor = null;
        try {
            assetFileDescriptor = assetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assetFileDescriptor;
    }
}

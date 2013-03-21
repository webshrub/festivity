package com.webshrub.festivity.holi.androidapp;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WallpaperItemManager {
    private Context context;
    private List<WallpaperItem> wallpaperItemList;

    public WallpaperItemManager(Context context) {
        try {
            this.context = context;
            AssetManager assetManager = context.getAssets();
            String[] wallpaperNames = assetManager.list(FestivityConstants.WALLPAPER_ASSETS_DIR);
            wallpaperItemList = new ArrayList<WallpaperItem>(wallpaperNames.length);
            for (int i = 0, fileNamesLength = wallpaperNames.length; i < fileNamesLength; i++) {
                String wallpaperUri = FestivityConstants.WALLPAPER_ASSETS_DIR + "/" + wallpaperNames[i];
                String wallpaperAssetUri = "assets://" + FestivityConstants.WALLPAPER_ASSETS_DIR + "/" + wallpaperNames[i];
                wallpaperItemList.add(new WallpaperItem(i, wallpaperNames[i], wallpaperUri, wallpaperAssetUri));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<WallpaperItem> getWallpaperItemList() {
        return wallpaperItemList;
    }

    public WallpaperItem getWallpaperItemAt(int position) {
        return wallpaperItemList.get(position);
    }

    public void setWallpaper(WallpaperItem currentWallpaperItem) {
        try {
            File copiedWallpaperFile = copyWallpaperToStorage(currentWallpaperItem);
            Bitmap wallpaper = BitmapFactory.decodeFile(copiedWallpaperFile.getAbsolutePath());
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            wallpaperManager.setBitmap(wallpaper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File copyWallpaperToStorage(WallpaperItem currentWallpaperItem) throws IOException {
        boolean externalStoragePresent = FestivityUtility.getInstance().isExternalStoragePresent(context);
        if (externalStoragePresent) {
            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/" + FestivityConstants.WALLPAPER_STORAGE_DIR);
            folder.mkdirs();
            String fileName = currentWallpaperItem.getTeaser();
            File destinationFile = new File(folder, fileName);
            byte[] buffer = new byte[1024];
            AssetFileDescriptor sourceFileDescriptor = context.getAssets().openFd(currentWallpaperItem.getDetails());
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

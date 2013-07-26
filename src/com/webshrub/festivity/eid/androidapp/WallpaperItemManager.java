package com.webshrub.festivity.eid.androidapp;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
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
                String name = wallpaperNames[i];
                String assetUri = FestivityConstants.WALLPAPER_ASSETS_DIR + "/" + wallpaperNames[i];
                String displayAssetUri = "assets://" + FestivityConstants.WALLPAPER_ASSETS_DIR + "/" + wallpaperNames[i];
                wallpaperItemList.add(new WallpaperItem(i, name, assetUri, displayAssetUri));
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
            File copiedWallpaperFile = FestivityUtility.copyFestivityItemToStorage(context, currentWallpaperItem, FestivityConstants.WALLPAPER_STORAGE_DIR);
            Bitmap wallpaper = BitmapFactory.decodeFile(copiedWallpaperFile.getAbsolutePath());
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            wallpaperManager.setBitmap(wallpaper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

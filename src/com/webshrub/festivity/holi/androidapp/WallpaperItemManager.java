package com.webshrub.festivity.holi.androidapp;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WallpaperItemManager {
    private List<WallpaperItem> wallpaperItemList;

    public WallpaperItemManager(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            String[] wallpaperNames = assetManager.list(FestivityConstants.WALLPAPERS_DIR);
            wallpaperItemList = new ArrayList<WallpaperItem>(wallpaperNames.length);
            for (int i = 0, fileNamesLength = wallpaperNames.length; i < fileNamesLength; i++) {
                String wallpaperUri = "assets://" + FestivityConstants.WALLPAPERS_DIR + "/" + wallpaperNames[i];
                wallpaperItemList.add(new WallpaperItem(i, wallpaperNames[i], wallpaperUri));
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
}

package com.webshrub.festivity.holi.androidapp;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

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
            File copiedWallpaperFile = copyWallpaperToStorage(currentWallpaperItem);
            Bitmap wallpaper = BitmapFactory.decodeFile(copiedWallpaperFile.getAbsolutePath());
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            wallpaperManager.setBitmap(wallpaper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File copyWallpaperToStorage(WallpaperItem currentWallpaperItem) throws IOException {
        boolean externalStoragePresent = FestivityUtility.isExternalStoragePresent(context);
        if (externalStoragePresent) {
            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/" + FestivityConstants.WALLPAPER_STORAGE_DIR);
            folder.mkdirs();
            String fileName = currentWallpaperItem.getName();
            File destinationFile = new File(folder, fileName);
            byte[] buffer = new byte[1024];
            AssetFileDescriptor sourceFileDescriptor = context.getAssets().openFd(currentWallpaperItem.getAssetUri());
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

    public void shareWallpaper(WallpaperItem currentWallpaperItem) {
        Uri wallpaperUri = Uri.parse(FestivityConstants.FESTIVITY_CONTENT_PROVIDER_AUTHORITY + currentWallpaperItem.getAssetUri());
        String extension = currentWallpaperItem.getAssetUri().substring(currentWallpaperItem.getAssetUri().lastIndexOf('.') + 1);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension));
        shareIntent.putExtra(Intent.EXTRA_STREAM, wallpaperUri);
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, currentWallpaperItem.getName());
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, currentWallpaperItem.getName());
        context.startActivity(shareIntent);
    }
}

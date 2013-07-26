package com.webshrub.greetings.eid.androidapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/20/13
 * Time: 10:01 PM
 */
public class WallpaperItemListAdapter extends BaseAdapter {
    private Context context;
    private WallpaperItemManager wallpaperItemManager;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.ic_stub)
            .showImageForEmptyUri(R.drawable.ic_empty)
            .showImageOnFail(R.drawable.ic_error)
            .cacheInMemory()
            .cacheOnDisc()
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public WallpaperItemListAdapter(Context context) {
        this.context = context;
        this.wallpaperItemManager = new WallpaperItemManager(context);
    }

    @Override
    public int getCount() {
        return wallpaperItemManager.getWallpaperItemList().size();
    }

    @Override
    public Object getItem(int position) {
        return wallpaperItemManager.getWallpaperItemAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView == null) {
            imageView = (ImageView) ((Activity) context).getLayoutInflater().inflate(R.layout.wallpaper_item_grid_image, parent, false);
        } else {
            imageView = (ImageView) convertView;
        }
        ImageLoader.getInstance().displayImage(wallpaperItemManager.getWallpaperItemAt(position).getDisplayAssetUri(), imageView, options);
        return imageView;
    }
}

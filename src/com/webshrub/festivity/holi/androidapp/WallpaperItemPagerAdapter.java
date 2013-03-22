package com.webshrub.festivity.holi.androidapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/20/13
 * Time: 9:50 PM
 */
class WallpaperItemPagerAdapter extends PagerAdapter {
    private Context context;
    private WallpaperItemManager wallpaperItemManager;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.ic_empty)
            .showImageOnFail(R.drawable.ic_error)
            .resetViewBeforeLoading()
            .cacheOnDisc()
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new FadeInBitmapDisplayer(300))
            .build();

    public WallpaperItemPagerAdapter(Context context) {
        this.context = context;
        this.wallpaperItemManager = new WallpaperItemManager(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(View container) {
    }

    @Override
    public int getCount() {
        return wallpaperItemManager.getWallpaperItemList().size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = ((Activity) context).getLayoutInflater().inflate(R.layout.item_pager_image, view, false);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
        ImageLoader.getInstance().displayImage(wallpaperItemManager.getWallpaperItemAt(position).getDisplayAssetUri(), imageView, options, new WallpaperItemLoadingListener(spinner));
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View container) {
    }

    private class WallpaperItemLoadingListener extends SimpleImageLoadingListener {
        private ProgressBar spinner;

        public WallpaperItemLoadingListener(ProgressBar spinner) {
            this.spinner = spinner;
        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            String message = null;
            switch (failReason.getType()) {
                case IO_ERROR:
                    message = "Input/Output error";
                    break;
                case DECODING_ERROR:
                    message = "Image can't be decoded";
                    break;
                case NETWORK_DENIED:
                    message = "Downloads are denied";
                    break;
                case OUT_OF_MEMORY:
                    message = "Out Of Memory error";
                    break;
                case UNKNOWN:
                    message = "Unknown error";
                    break;
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            spinner.setVisibility(View.GONE);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            spinner.setVisibility(View.GONE);
        }
    }
}

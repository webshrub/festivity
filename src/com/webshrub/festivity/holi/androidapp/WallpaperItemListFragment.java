package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.app.SherlockFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 2/28/13
 * Time: 1:38 PM
 */
public class WallpaperItemListFragment extends SherlockFragment {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private String[] imageUris = FestivityConstants.IMAGE_URIS;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.ic_stub)
            .showImageForEmptyUri(R.drawable.ic_empty)
            .showImageOnFail(R.drawable.ic_error)
            .cacheInMemory()
            .cacheOnDisc()
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.ac_image_grid, container, false);
        AbsListView listView = (GridView) view.findViewById(R.id.gridview);
        listView.setAdapter(new WallpaperItemAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startWallpaperItemDetailsActivity(position);
            }
        });
        return view;
    }

    public class WallpaperItemAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return imageUris.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ImageView imageView;
            if (convertView == null) {
                imageView = (ImageView) getSherlockActivity().getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
            } else {
                imageView = (ImageView) convertView;
            }
            imageLoader.displayImage(imageUris[position], imageView, options);
            return imageView;
        }
    }

    private void startWallpaperItemDetailsActivity(int position) {
        Intent intent = new Intent(getSherlockActivity(), WallpaperItemDetailsActivity.class);
        intent.putExtra(FestivityConstants.IMAGES, imageUris);
        intent.putExtra(FestivityConstants.IMAGE_POSITION, position);
        startActivity(intent);
    }
}

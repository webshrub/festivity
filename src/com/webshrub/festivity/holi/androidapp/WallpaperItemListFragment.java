package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import com.actionbarsherlock.app.SherlockFragment;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 2/28/13
 * Time: 1:38 PM
 */
public class WallpaperItemListFragment extends SherlockFragment {
    private WallpaperItemListAdapter wallpaperItemListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wallpaperItemListAdapter = new WallpaperItemListAdapter(getSherlockActivity());
        View view = inflater.inflate(R.layout.ac_image_grid, container, false);
        AbsListView listView = (GridView) view.findViewById(R.id.gridview);
        listView.setAdapter(wallpaperItemListAdapter);
        listView.setOnItemClickListener(new WallpaperItemOnClickListener());
        return view;
    }

    private class WallpaperItemOnClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getSherlockActivity(), WallpaperItemDetailsActivity.class);
            intent.putExtra(FestivityConstants.FESTIVITY_ITEM, (WallpaperItem) wallpaperItemListAdapter.getItem(position));
            startActivity(intent);
        }
    }
}

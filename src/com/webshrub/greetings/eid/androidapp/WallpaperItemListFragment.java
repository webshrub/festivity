package com.webshrub.greetings.eid.androidapp;

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
    @SuppressWarnings({"unchecked", "rawtypes"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wallpaperItemListAdapter = new WallpaperItemListAdapter(getSherlockActivity());
        View view = inflater.inflate(R.layout.wallpaper_item_grid, container, false);
        AbsListView listView = (GridView) view.findViewById(R.id.wallpaperItemGridView);
        ((AdapterView) listView).setAdapter(wallpaperItemListAdapter);
        listView.setOnItemClickListener(new WallpaperItemOnClickListener());
        return view;
    }

    private class WallpaperItemOnClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getSherlockActivity(), FestivityItemDetailsActivity.class);
            intent.putExtra(FestivityConstants.FESTIVITY_ITEM, (WallpaperItem) wallpaperItemListAdapter.getItem(position));
            startActivity(intent);
        }
    }
}

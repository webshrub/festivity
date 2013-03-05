package com.webshrub.festivity.androidapp;

import android.os.Bundle;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 2/28/13
 * Time: 1:38 PM
 */
public class WallpaperListFragment extends FestivityListFragment<Wallpaper> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        resourceLayoutId = android.R.layout.simple_list_item_1;
        data = new Wallpaper[]{};
        super.onActivityCreated(savedInstanceState);
    }
}

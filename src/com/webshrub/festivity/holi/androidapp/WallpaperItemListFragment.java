package com.webshrub.festivity.holi.androidapp;

import android.os.Bundle;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 2/28/13
 * Time: 1:38 PM
 */
public class WallpaperItemListFragment extends FestivityItemListFragment<WallpaperItem> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        resourceLayoutId = android.R.layout.simple_list_item_1;
        data = new WallpaperItem[Shakespeare.TITLES.length];
        String[] titles = Shakespeare.TITLES;
        for (int i = 0, titlesLength = titles.length; i < titlesLength; i++) {
            String title = titles[i];
            String details = Shakespeare.DIALOGUE[i];
            data[i] = new WallpaperItem(i, title, details);
        }
        super.onActivityCreated(savedInstanceState);
    }
}

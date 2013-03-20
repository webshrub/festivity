package com.webshrub.festivity.holi.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class WallpaperItemDetailsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image_pager);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new WallpaperItemPagerAdapter(this));
        int pagerPosition = ((WallpaperItem) getIntent().getExtras().getParcelable(FestivityConstants.FESTIVITY_ITEM)).getId();
        pager.setCurrentItem(pagerPosition);
    }
}
package com.webshrub.festivity.androidapp;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 1:03 AM
 */
public class FestivityItemDetailsActivity<T extends FestivityItem> extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FestivityItemDetailsFragment detailsFragment = null;
        FestivityItem item = getIntent().getExtras().getParcelable(FestivityConstants.FESTIVITY_ITEM_KEY);
        if (item instanceof MessageItem) {
            detailsFragment = new MessageItemDetailsFragment();
            detailsFragment.setArguments(getIntent().getExtras());
        } else if (item instanceof RingtoneItem) {
            detailsFragment = new RingtoneItemDetailsFragment();
            detailsFragment.setArguments(getIntent().getExtras());
        } else if (item instanceof WallpaperItem) {
            detailsFragment = new WallpaperItemDetailsFragment();
            detailsFragment.setArguments(getIntent().getExtras());
        }
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, detailsFragment).commit();
    }
}

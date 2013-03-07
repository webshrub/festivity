package com.webshrub.festivity.androidapp;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 1:03 AM
 */
public class FestivityDetailsActivity<T extends FestivityItem> extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // During initial setup, plug in the details fragment.
        FestivityDetailsFragment detailsFragment = new FestivityDetailsFragment();
        detailsFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, detailsFragment).commit();
    }
}

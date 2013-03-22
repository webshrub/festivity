package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/9/13
 * Time: 2:24 PM
 */
public class ContactPickerActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Create the list fragment and add it as our sole content.
        if (fragmentManager.findFragmentById(android.R.id.content) == null) {
            ContactPickerListFragment contactPickerListFragment = new ContactPickerListFragment();
            contactPickerListFragment.setArguments(getIntent().getExtras());
            fragmentManager.beginTransaction().add(android.R.id.content, contactPickerListFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(this, FestivityItemDetailsActivity.class);
                intent.putExtra(FestivityConstants.FESTIVITY_ITEM, getIntent().getExtras().getParcelable(FestivityConstants.FESTIVITY_ITEM));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

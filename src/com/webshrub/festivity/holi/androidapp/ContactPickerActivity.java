package com.webshrub.festivity.holi.androidapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.actionbarsherlock.app.SherlockFragmentActivity;

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Create the list fragment and add it as our sole content.
        if (fragmentManager.findFragmentById(android.R.id.content) == null) {
            ContactPickerListFragment contactPickerListFragment = new ContactPickerListFragment();
            contactPickerListFragment.setArguments(getIntent().getExtras());
            fragmentManager.beginTransaction().add(android.R.id.content, contactPickerListFragment).commit();
        }
    }
}

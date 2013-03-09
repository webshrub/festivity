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
        FragmentManager fm = getSupportFragmentManager();
        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
            ContactPickerListFragment list = new ContactPickerListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }
}

/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webshrub.greetings.eid.androidapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.sbstrm.appirater.Appirater;

import java.util.ArrayList;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI
 * that switches between tabs and also allows the user to perform horizontal
 * flicks to move between the tabs.
 */
public class FestivityHomeActivity extends SherlockFragmentActivity {
    TabHost mTabHost;
    ViewPager mViewPager;
    TabsAdapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Appirater.appLaunched(this);
        setContentView(R.layout.fragment_tabs_pager);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager) findViewById(R.id.pager);

        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

        mTabsAdapter.addTab(mTabHost.newTabSpec("messages").setIndicator(getString(R.string.messages)), MessageItemListFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("ringtones").setIndicator(getString(R.string.ringtones)), RingtoneItemListFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("wallpapers").setIndicator(getString(R.string.wallpapers)), WallpaperItemListFragment.class, null);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

    /**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
    public static class TabsAdapter extends FragmentPagerAdapter implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(String tabId) {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_rate_us:
                String marketUrl = "market://details?id=com.webshrub.greetings.eid.androidapp";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(marketUrl)));
                return true;
            case R.id.menu_share_on_facebook:
                String facebookUrl = "https://www.facebook.com/dialog/feed?app_id=300503886742748&link=https://www.facebook.com/pages/Festivity-Holi/474603645926400&picture=http://pure-cove-2261.herokuapp.com/images/logo_64.png&name=Festivity%20Holi&caption=Festivity%20Holi&description=Festivity%20Holi&redirect_uri=http://pure-cove-2261.herokuapp.com/";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                return true;
            case R.id.menu_share_on_twitter:
                String twitterUrl = "https://twitter.com/intent/tweet?text=Friends%2C%20I%20am%20using%20%23dndmanager%20to%20block%20pesky%20Calls%2FSMS.%20Download%20it%20http%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.webshrub.moonwalker.androidapp";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl)));
                return true;
            case R.id.menu_settings:
                Intent preferenceIntent = new Intent(this, FestivityPreferenceActivity.class);
                preferenceIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(preferenceIntent);
                return true;
            case R.id.menu_about:
                Intent aboutIntent = new Intent(this, FestivityAboutActivity.class);
                startActivity(aboutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

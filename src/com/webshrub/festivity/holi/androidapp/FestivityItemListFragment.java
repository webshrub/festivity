package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SearchViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 2/28/13
 * Time: 1:38 PM
 */
public class FestivityItemListFragment<T extends FestivityItem> extends SherlockListFragment {
    protected T[] data;
    protected int resourceLayoutId;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("No data found.");
        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);
        getListView().setTextFilterEnabled(true);
        // Populate list with our static array of titles.
        setListAdapter(new FestivityItemListAdapter<T>(getActivity(), resourceLayoutId, data));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), FestivityItemDetailsActivity.class);
        intent.putExtra(FestivityConstants.FESTIVITY_ITEM, (FestivityItem) getListAdapter().getItem(position));
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        View searchView = searchItem.getActionView();
        SearchViewCompat.setOnQueryTextListener(searchView, new FestivityOnQueryTextListenerCompat());
    }

    private class FestivityOnQueryTextListenerCompat extends SearchViewCompat.OnQueryTextListenerCompat {
        @Override
        public boolean onQueryTextChange(String newText) {
            // Called when the action bar search text has changed.  Update the search filter, and restart the loader to do a new query with this filter.
            if (TextUtils.isEmpty(newText)) {
                getListView().clearTextFilter();
            } else {
                getListView().setFilterText(newText);
            }
            return true;
        }
    }
}

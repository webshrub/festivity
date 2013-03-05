package com.webshrub.festivity.androidapp;

import android.os.Bundle;
import android.support.v4.widget.SearchViewCompat;
import android.text.TextUtils;
import android.util.Log;
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
public class FestivityListFragment<T> extends SherlockListFragment {
    protected T[] data;
    protected int resourceLayoutId;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Give some text to display if there is no data.  In a real application this would come from a resource.
        setEmptyText("No data found.");
        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);
        getListView().setTextFilterEnabled(true);
        setListAdapter(new FestivityListAdapter<T>(getActivity(), resourceLayoutId, data));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Place an action bar item for searching.
        MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        View searchView = SearchViewCompat.newSearchView(getActivity());
        if (searchView != null) {
            SearchViewCompat.setOnQueryTextListener(searchView,
                    new SearchViewCompat.OnQueryTextListenerCompat() {
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
                    });
            item.setActionView(searchView);
        }
    }
}

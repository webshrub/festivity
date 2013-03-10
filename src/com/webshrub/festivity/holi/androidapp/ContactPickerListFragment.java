package com.webshrub.festivity.holi.androidapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/9/13
 * Time: 3:06 PM
 */
public class ContactPickerListFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // These are the Contacts rows that we will retrieve.
    private static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{Contacts.People._ID, Contacts.People.DISPLAY_NAME,};
    // This is the Adapter being used to display the list's data.
    private SimpleCursorAdapter mAdapter;
    // If non-null, this is the current filter the user has provided.
    private String mCurFilter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        setEmptyText("No contacts found.");
        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);
        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_checked, null, new String[]{Contacts.People.DISPLAY_NAME}, new int[]{android.R.id.text1}, 0);
        setListAdapter(mAdapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(new ContactPickerMultiChoiceModeListener());
        // Start out with a progress indicator.
        setListShown(false);
        // Prepare the loader.  Either re-connect with an existing one, or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        View searchView = searchItem.getActionView();
        SearchViewCompat.setOnQueryTextListener(searchView, new ContactPickerOnQueryTextListenerCompat());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "Item clicked: " + id, Toast.LENGTH_LONG).show();
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are currently filtering.
        Uri baseUri = mCurFilter != null ? Uri.withAppendedPath(Contacts.People.CONTENT_FILTER_URI, Uri.encode(mCurFilter)) : Contacts.People.CONTENT_URI;
        // Now create and return a CursorLoader that will take care of creating a Cursor for the data being displayed.
        String select = "((" + Contacts.People.DISPLAY_NAME + " NOTNULL) AND (" + Contacts.People.DISPLAY_NAME + " != '' ))";
        return new CursorLoader(getActivity(), baseUri, CONTACTS_SUMMARY_PROJECTION, select, null, Contacts.People.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the old cursor once we return.)
        mAdapter.swapCursor(data);
        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished() above is about to be closed.  We need to make sure we are no longer using it.
        mAdapter.swapCursor(null);
    }

    private class ContactPickerOnQueryTextListenerCompat extends SearchViewCompat.OnQueryTextListenerCompat {
        @Override
        public boolean onQueryTextChange(String newText) {
            // Called when the action bar search text has changed.  Update the search filter, and restart the loader to do a new query with this filter.
            mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
            getLoaderManager().restartLoader(0, null, ContactPickerListFragment.this);
            return true;
        }
    }

    private class ContactPickerMultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {
        @Override
        public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

        /**
         * This will be invoked when action mode is created. In our case , it is on long clicking a menu item
         */
        @Override
        public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
            getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
            return true;
        }

        /**
         * Invoked when an action in the action mode is clicked
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, android.view.MenuItem item) {
            Toast.makeText(getActivity(), "Applying " + item.getTitle() + " on " + getListView().getCheckedItemCount() + " Rivers \n", Toast.LENGTH_LONG).show();
            return false;
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        }
    }
}

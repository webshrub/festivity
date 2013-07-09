package com.webshrub.festivity.holi.androidapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/9/13
 * Time: 3:06 PM
 */
public class ContactPickerListFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // These are the Contacts rows that we will retrieve.
    private String[] CONTACTS_SUMMARY_PROJECTION = new String[]{ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
    // This is the Adapter being used to display the list's data.
    private ArrayAdapter<Contact> mAdapter;
    // If non-null, this is the current filter the user has provided.
    private ActionMode mActionMode;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        setEmptyText("No contacts found.");
        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);
        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new ArrayAdapter<Contact>(getSherlockActivity(), android.R.layout.simple_list_item_checked);
        setListAdapter(mAdapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // Start out with a progress indicator.
        setListShown(false);
        // Prepare the loader.  Either re-connect with an existing one, or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                SparseBooleanArray checked = getListView().getCheckedItemPositions();
                List<Contact> contactList = new ArrayList<Contact>();
                for (int position = 0; position < getListView().getCount(); position++) {
                    if (checked.get(position)) {
                        contactList.add(mAdapter.getItem(position));
                    }
                }
                if (contactList.size() == 0) {
                    Toast.makeText(getActivity(), "Please choose some contacts\n", Toast.LENGTH_LONG).show();
                    return true;
                }
                String message = getArguments().getString(FestivityConstants.MESSAGE_TEXT);
                new MessageItemSenderTask(getSherlockActivity(), message).execute(contactList.toArray(new Contact[contactList.size()]));
                selectDeselectAll(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mActionMode == null) {
            mActionMode = getSherlockActivity().startActionMode(new ContactPickerMultiChoiceModeListener());
        }
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are currently filtering.
        Uri baseUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // Now create and return a CursorLoader that will take care of creating a Cursor for the data being displayed.
        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND (" + ContactsContract.Contacts.DISPLAY_NAME + " != '' )" + "AND (" + ContactsContract.Contacts.HAS_PHONE_NUMBER + " != '0'" + "))";
        return new CursorLoader(getActivity(), baseUri, CONTACTS_SUMMARY_PROJECTION, select, null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        while (cursor.moveToNext()) {
            int displayNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String displayName = cursor.getString(displayNameIndex).trim();
            String number = cursor.getString(numberIndex).trim();
            Contact contact = new Contact(displayName, number);
            mAdapter.add(contact);
        }
        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished() above is about to be closed.  We need to make sure we are no longer using it.
    }

    private class ContactPickerMultiChoiceModeListener implements ActionMode.Callback {
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_contact_list_context, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.select_all:
                    selectDeselectAll(true);
                    return true;
                case R.id.select_none:
                    selectDeselectAll(false);
                    return true;
                default:
                    mode.finish();
                    return false;
            }
        }
    }

    private void selectDeselectAll(boolean select) {
        for (int count = 0; count < mAdapter.getCount(); count++) {
            getListView().setItemChecked(count, select);
        }
    }
}
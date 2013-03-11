package com.webshrub.festivity.holi.androidapp;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.CheckedTextView;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/11/13
 * Time: 5:38 PM
 */
public class ContactRowViewBinder implements SimpleCursorAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        int displayNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        if (columnIndex == displayNameIndex) {
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String displayName = cursor.getString(displayNameIndex).trim();
            String number = cursor.getString(numberIndex).trim();
            ((CheckedTextView) view).setText(displayName + "(" + number + ")");
            return true;
        }
        return false;
    }
}

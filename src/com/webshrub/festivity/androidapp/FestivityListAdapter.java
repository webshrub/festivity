package com.webshrub.festivity.androidapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/5/13
 * Time: 1:05 PM
 */
public class FestivityListAdapter<T> extends ArrayAdapter<T> {

    public FestivityListAdapter(Context context, int layoutResourceId, T[] data) {
        super(context, layoutResourceId, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}

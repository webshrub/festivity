package com.webshrub.festivity.androidapp;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;

public class FestivityItemDetailsFragment<T extends FestivityItem> extends SherlockFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroller.addView(text);
        text.setText(((FestivityItem) getArguments().getParcelable(FestivityListFragment.FESTIVITY_EXTRA_KEY)).getDetails());
        return scroller;
    }
}

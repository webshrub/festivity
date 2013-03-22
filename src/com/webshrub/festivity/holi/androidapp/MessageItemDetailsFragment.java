package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 2:40 PM
 */
public class MessageItemDetailsFragment extends FestivityItemDetailsFragment<MessageItem> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_sms, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send_to_contacts:
                Intent contactPickerIntent = new Intent(getActivity(), ContactPickerActivity.class);
                contactPickerIntent.putExtra(FestivityConstants.FESTIVITY_ITEM, getArguments().getParcelable(FestivityConstants.FESTIVITY_ITEM));
                startActivity(contactPickerIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroller.addView(text);
        try {
            AssetManager assetManager = getSherlockActivity().getAssets();
            InputStream inputStream = assetManager.open(((MessageItem) getArguments().getParcelable(FestivityConstants.FESTIVITY_ITEM)).getAssetUri());
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            text.setText(new String(buffer));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scroller;
    }
}

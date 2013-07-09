package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 2:40 PM
 */
public class MessageItemDetailsFragment extends FestivityItemDetailsFragment<MessageItem> {
    private ViewPager viewPager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        int pagerPosition = ((MessageItem) getSherlockActivity().getIntent().getExtras().getParcelable(FestivityConstants.FESTIVITY_ITEM)).getId();
        viewPager.setCurrentItem(pagerPosition);
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
                EditText messageTextView = (EditText) viewPager.findViewById(R.id.messageTextView);
                Intent contactPickerIntent = new Intent(getActivity(), ContactPickerActivity.class);
                contactPickerIntent.putExtra("messageText", messageTextView.getText().toString());
                startActivity(contactPickerIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.festivity_item_details_pager, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setAdapter(new MessageItemPagerAdapter(getSherlockActivity()));
        return view;
    }
}
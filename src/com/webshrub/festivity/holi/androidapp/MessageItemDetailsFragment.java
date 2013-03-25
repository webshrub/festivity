package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
        View view = inflater.inflate(R.layout.message_details, container, false);
        TextView messageTextView = (TextView) view.findViewById(R.id.messageTextView);
        String message = new MessageItemManager(getSherlockActivity()).getMessageString((MessageItem) getArguments().getParcelable(FestivityConstants.FESTIVITY_ITEM));
        messageTextView.setText(message);
        return view;
    }
}
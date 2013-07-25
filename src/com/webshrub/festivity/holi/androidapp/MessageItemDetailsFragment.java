package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.UnderlinePageIndicator;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 2:40 PM
 */
public class MessageItemDetailsFragment extends FestivityItemDetailsFragment<MessageItem> {
    private MessageItemManager messageItemManager;
    private ViewPager viewPager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        messageItemManager = new MessageItemManager(getSherlockActivity());
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
            case R.id.menu_share_message:
                int currentPosition = viewPager.getCurrentItem();
                MessageItem currentMessageItem = messageItemManager.getMessageItemAt(currentPosition);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, currentMessageItem.getName());
                shareIntent.putExtra(Intent.EXTRA_TEXT, messageItemManager.getMessageString(currentMessageItem));
                startActivity(shareIntent);
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
        UnderlinePageIndicator pageIndicator = (UnderlinePageIndicator) view.findViewById(R.id.pageIndicator);
        pageIndicator.setViewPager(viewPager);
        pageIndicator.setFades(false);
        return view;
    }
}
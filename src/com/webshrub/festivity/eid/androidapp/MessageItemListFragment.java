package com.webshrub.festivity.eid.androidapp;

import android.os.Bundle;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 2/28/13
 * Time: 1:38 PM
 */
public class MessageItemListFragment extends FestivityItemListFragment<MessageItem> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        resourceLayoutId = android.R.layout.simple_list_item_1;
        List<MessageItem> messageItemList = new MessageItemManager(getSherlockActivity()).getMessageItemList();
        data = messageItemList.toArray(new MessageItem[messageItemList.size()]);
        super.onActivityCreated(savedInstanceState);
    }
}
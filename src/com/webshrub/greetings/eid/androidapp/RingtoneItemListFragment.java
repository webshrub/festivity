package com.webshrub.greetings.eid.androidapp;

import android.os.Bundle;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 2/28/13
 * Time: 1:38 PM
 */
public class RingtoneItemListFragment extends FestivityItemListFragment<RingtoneItem> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        resourceLayoutId = android.R.layout.simple_list_item_1;
        List<RingtoneItem> ringtoneItemList = new RingtoneItemManager(getSherlockActivity()).getRingtoneItemList();
        data = ringtoneItemList.toArray(new RingtoneItem[ringtoneItemList.size()]);
        super.onActivityCreated(savedInstanceState);
    }
}

package com.webshrub.festivity.androidapp;

import android.R;
import android.os.Bundle;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 2/28/13
 * Time: 1:38 PM
 */
public class MessageListFragment extends FestivityListFragment<Message> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        resourceLayoutId = R.layout.simple_list_item_1;
        data = new Message[Shakespeare.TITLES.length];
        String[] titles = Shakespeare.TITLES;
        for (int i = 0, titlesLength = titles.length; i < titlesLength; i++) {
            String title = titles[i];
            data[i] = new Message(i, title, title);
        }
        super.onActivityCreated(savedInstanceState);
    }
}
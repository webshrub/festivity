package com.webshrub.festivity.holi.androidapp;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/20/13
 * Time: 9:50 PM
 */
class MessageItemPagerAdapter extends PagerAdapter {
    private Context context;
    private MessageItemManager messageItemManager;

    public MessageItemPagerAdapter(Context context) {
        this.context = context;
        this.messageItemManager = new MessageItemManager(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(View container) {
    }

    @Override
    public int getCount() {
        return messageItemManager.getMessageItemList().size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = ((Activity) context).getLayoutInflater().inflate(R.layout.message_item_details, view, false);
        EditText messageTextView = (EditText) imageLayout.findViewById(R.id.messageTextView);
        messageTextView.setText(messageItemManager.getMessageString(messageItemManager.getMessageItemAt(position)));
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View container) {
    }
}

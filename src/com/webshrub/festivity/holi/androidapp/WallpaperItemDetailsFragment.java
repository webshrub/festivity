package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ScrollView;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 2:40 PM
 */
public class WallpaperItemDetailsFragment extends FestivityItemDetailsFragment<RingtoneItem> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_wallpaper, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_set_wallpaper:
                return true;
            case R.id.menu_share_wallpaper:
                String path = "/mnt/sdcard/dir1/sample_1.jpg";
                Intent share = new Intent(Intent.ACTION_SEND);
                MimeTypeMap map = MimeTypeMap.getSingleton(); //mapping from extension to mimetype
                String ext = path.substring(path.lastIndexOf('.') + 1);
                String mime = map.getMimeTypeFromExtension(ext);
                share.setType(mime); // might be text, sound, whatever
                Uri uri = Uri.fromFile(new File(path));
                share.putExtra(Intent.EXTRA_STREAM, uri);//using a string here didnt work for me
                Log.d(FestivityItemDetailsActivity.TAG, "share " + uri + " ext:" + ext + " mime:" + mime);
                startActivity(Intent.createChooser(share, "share"));
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
        text.setText(((WallpaperItem) getArguments().getParcelable(FestivityConstants.FESTIVITY_ITEM)).getDetails());
        return scroller;
    }
}

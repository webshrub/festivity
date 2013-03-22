package com.webshrub.festivity.holi.androidapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 2:40 PM
 */
public class WallpaperItemDetailsFragment extends FestivityItemDetailsFragment<WallpaperItem> {
    private WallpaperItemManager wallpaperItemManager;
    private ViewPager viewPager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        wallpaperItemManager = new WallpaperItemManager(getSherlockActivity());
        int pagerPosition = ((WallpaperItem) getSherlockActivity().getIntent().getExtras().getParcelable(FestivityConstants.FESTIVITY_ITEM)).getId();
        viewPager.setCurrentItem(pagerPosition);
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
                int currentPosition = viewPager.getCurrentItem();
                WallpaperItem currentWallpaperItem = wallpaperItemManager.getWallpaperItemAt(currentPosition);
                wallpaperItemManager.setWallpaper(currentWallpaperItem);
                Toast.makeText(getSherlockActivity(), "Wallpaper set successfully.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_share_wallpaper:
                currentPosition = viewPager.getCurrentItem();
                currentWallpaperItem = wallpaperItemManager.getWallpaperItemAt(currentPosition);
                Uri wallpaperUri = Uri.parse(FestivityConstants.FESTIVITY_CONTENT_PROVIDER_AUTHORITY + currentWallpaperItem.getDetails());
                String extension = currentWallpaperItem.getDetails().substring(currentWallpaperItem.getDetails().lastIndexOf('.') + 1);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension));
                shareIntent.putExtra(Intent.EXTRA_STREAM, wallpaperUri);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, currentWallpaperItem.getTeaser());
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, currentWallpaperItem.getTeaser());
                startActivity(shareIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.ac_image_pager, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setAdapter(new WallpaperItemPagerAdapter(getSherlockActivity()));
        return view;
    }
}

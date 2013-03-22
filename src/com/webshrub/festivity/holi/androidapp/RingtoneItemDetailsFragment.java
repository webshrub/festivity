package com.webshrub.festivity.holi.androidapp;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/7/13
 * Time: 2:40 PM
 */
public class RingtoneItemDetailsFragment extends FestivityItemDetailsFragment<RingtoneItem> implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    private ImageButton btnPlay;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private SeekBar songProgressBar;
    private TextView songTitle;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private boolean isShuffle;
    private boolean isRepeat;
    private RingtoneItem currentSong;
    private RingtoneItemManager songManager;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable updateTimerTask;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        isShuffle = false;
        isRepeat = false;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        handler = new Handler();
        updateTimerTask = new Runnable() {
            public void run() {
                long totalDuration = mediaPlayer.getDuration();
                long currentDuration = mediaPlayer.getCurrentPosition();
                songTotalDurationLabel.setText(FestivityUtility.milliSecondsToTimer(totalDuration));
                songCurrentDurationLabel.setText(FestivityUtility.milliSecondsToTimer(currentDuration));
                int progress = FestivityUtility.getProgressPercentage(currentDuration, totalDuration);
                songProgressBar.setProgress(progress);
                handler.postDelayed(this, 100);
            }
        };
        currentSong = getArguments().getParcelable(FestivityConstants.FESTIVITY_ITEM);
        songManager = new RingtoneItemManager(getSherlockActivity());
        playSong(currentSong);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_ringtone, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_set_ringtone:
                songManager.setRingtone(currentSong);
                Toast.makeText(getSherlockActivity(), "Ringtone set successfully.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_share_ringtone:
                songManager.shareRingtone(currentSong);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.player, container, false);

        ImageButton btnForward = (ImageButton) view.findViewById(R.id.btnForward);
        btnForward.setOnClickListener(new ButtonForwardOnClickListener());
        ImageButton btnBackward = (ImageButton) view.findViewById(R.id.btnBackward);
        btnBackward.setOnClickListener(new ButtonBackwardOnClickListener());
        ImageButton btnNext = (ImageButton) view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new ButtonNextOnClickListener());
        ImageButton btnPrevious = (ImageButton) view.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new ButtonPreviousOnClickListener());

        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new ButtonPlayOnClickListener());

        btnRepeat = (ImageButton) view.findViewById(R.id.btnRepeat);
        btnRepeat.setOnClickListener(new ButtonRepeatOnClickListener());

        btnShuffle = (ImageButton) view.findViewById(R.id.btnShuffle);
        btnShuffle.setOnClickListener(new ButtonShuffleOnClickListener());

        songProgressBar = (SeekBar) view.findViewById(R.id.songProgressBar);
        songTitle = (TextView) view.findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) view.findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) view.findViewById(R.id.songTotalDurationLabel);
        songProgressBar.setOnSeekBarChangeListener(this);
        return view;
    }

    public void playSong(RingtoneItem currentSong) {
        try {
            mediaPlayer.reset();
            AssetFileDescriptor assetFileDescriptor = getSherlockActivity().getAssets().openFd(currentSong.getDetails());
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetFileDescriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
            songTitle.setText(currentSong.getTeaser());
            btnPlay.setImageResource(R.drawable.btn_pause);
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBar() {
        handler.postDelayed(updateTimerTask, 100);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(updateTimerTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(updateTimerTask);
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = FestivityUtility.progressToTimer(seekBar.getProgress(), totalDuration);
        mediaPlayer.seekTo(currentPosition);
        updateProgressBar();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (isRepeat) {
            playSong(currentSong);
        } else if (isShuffle) {
            currentSong = songManager.getRandomRingtoneItem();
            playSong(currentSong);
        } else {
            currentSong = songManager.getNextRingtoneItem(currentSong);
            playSong(currentSong);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimerTask);
        mediaPlayer.release();
    }

    private class ButtonPlayOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (mediaPlayer.isPlaying()) {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.btn_play);
                }
            } else {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.btn_pause);
                }
            }
        }
    }

    private class ButtonForwardOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int seekForwardTime = 5000;
            if (currentPosition + seekForwardTime <= mediaPlayer.getDuration()) {
                mediaPlayer.seekTo(currentPosition + seekForwardTime);
            } else {
                mediaPlayer.seekTo(mediaPlayer.getDuration());
            }
        }
    }

    private class ButtonBackwardOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int seekBackwardTime = 5000;
            if (currentPosition - seekBackwardTime >= 0) {
                mediaPlayer.seekTo(currentPosition - seekBackwardTime);
            } else {
                mediaPlayer.seekTo(0);
            }
        }
    }

    private class ButtonNextOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            currentSong = songManager.getNextRingtoneItem(currentSong);
            playSong(currentSong);
        }
    }

    private class ButtonPreviousOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            currentSong = songManager.getPreviousRingtoneItem(currentSong);
            playSong(currentSong);
        }
    }

    private class ButtonRepeatOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (isRepeat) {
                isRepeat = false;
                Toast.makeText(getSherlockActivity(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                btnRepeat.setImageResource(R.drawable.btn_repeat);
            } else {
                isRepeat = true;
                Toast.makeText(getSherlockActivity(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                isShuffle = false;
                btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                btnShuffle.setImageResource(R.drawable.btn_shuffle);
            }
        }
    }

    private class ButtonShuffleOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (isShuffle) {
                isShuffle = false;
                Toast.makeText(getSherlockActivity(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                btnShuffle.setImageResource(R.drawable.btn_shuffle);
            } else {
                isShuffle = true;
                Toast.makeText(getSherlockActivity(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                isRepeat = false;
                btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                btnRepeat.setImageResource(R.drawable.btn_repeat);
            }
        }
    }
}

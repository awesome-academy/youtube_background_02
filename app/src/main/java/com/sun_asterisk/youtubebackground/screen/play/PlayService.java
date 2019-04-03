package com.sun_asterisk.youtubebackground.screen.play;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.utils.Action;
import com.sun_asterisk.youtubebackground.utils.Constant;
import com.sun_asterisk.youtubebackground.utils.notification.VideoNotificationControl;
import java.util.List;

public class PlayService extends Service {
    private IBinder mIBinder = new ServicePlay();
    private YouTubePlayer mYouTubePlayer;
    private YouTubePlayerSupportFragment mYouTubePlayerSupportFragment =
            new YouTubePlayerSupportFragment();
    private int mPosition;
    private VideoNotificationControl mVideoNotificationControl;
    private List<Video> mVideos;

    public PlayService() {
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, PlayService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mIBinder = new ServicePlay();
        mVideoNotificationControl = new VideoNotificationControl(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() == null) return START_NOT_STICKY;
        switch (intent.getAction()) {
            case Action.ACTION_PLAY_OR_PAUSE:
                actionPlayOrPause();
                break;
            case Action.ACTION_NEXT:
                actionNext();
                break;
            case Action.ACTION_PREVIOUS:
                actionPrevious();
                break;
            case Action.ACTION_STOP:
                actionStop();
                break;
            default:
                break;
        }
        return START_NOT_STICKY;
    }

    private void actionStop() {
        mYouTubePlayer.pause();
        stopForeground(true);
    }

    private void actionPrevious() {
        if (mPosition != 0) {
            mYouTubePlayer.previous();
            notificationChange();
        }
    }

    private void actionNext() {
        if (mPosition != (mVideos.size() - 1)) {
            mYouTubePlayer.next();
            notificationChange();
        }
    }

    private void actionPlayOrPause() {
        if (isCheckPlaying()) {
            mYouTubePlayer.pause();
        } else {
            mYouTubePlayer.play();
        }
    }

    public boolean isCheckPlaying() {
        return mYouTubePlayer.isPlaying();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public void playVideo(int position, FragmentTransaction transaction) {
        mPosition = position;
        mYouTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        transaction.add(R.id.youTubePlayerView, mYouTubePlayerSupportFragment).commit();
        mYouTubePlayerSupportFragment.initialize(Constant.KEY,
                new YouTubePlayer.OnInitializedListener() {

                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider arg0,
                            YouTubePlayer youTubePlayer, boolean isFullScreen) {
                        if (!isFullScreen) {
                            mYouTubePlayer = youTubePlayer;
                            mYouTubePlayer.setFullscreen(false);
                            mYouTubePlayer.loadPlaylist(Constant.PLAYLIST, mPosition, 0);
                            mYouTubePlayer.play();
                            updateNotification();
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider arg0,
                            YouTubeInitializationResult arg1) {
                    }
                });
    }

    private void updateNotification() {
        mVideoNotificationControl.updateNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class ServicePlay extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }

    public YouTubePlayerSupportFragment getYouTubePlayerSupportFragment() {
        return mYouTubePlayerSupportFragment;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setVideo(List<Video> video, int position) {
        mVideos = video;
        mPosition = position;
    }

    public List<Video> getVideo() {
        return mVideos;
    }

    private void notificationChange() {
        mVideoNotificationControl.updateNotification();
    }
}

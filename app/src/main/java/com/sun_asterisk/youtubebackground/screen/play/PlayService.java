package com.sun_asterisk.youtubebackground.screen.play;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.screen.main.MainActivity;
import com.sun_asterisk.youtubebackground.utils.Constant;
import java.util.List;

public class PlayService extends Service {
    private IBinder mIBinder = new ServicePlay();
    private YouTubePlayer mYouTubePlayer;
    private YouTubePlayerSupportFragment mYouTubePlayerSupportFragment =
            new YouTubePlayerSupportFragment();
    private int mPosition;

    public PlayService() {
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, PlayService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
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
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider arg0,
                            YouTubeInitializationResult arg1) {
                    }
                });
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
}

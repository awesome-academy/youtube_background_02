package com.sun_asterisk.youtubebackground.utils.notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.screen.main.MainActivity;
import com.sun_asterisk.youtubebackground.screen.play.PlayService;
import com.sun_asterisk.youtubebackground.utils.Action;
import java.util.List;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;
import static android.support.v4.media.app.NotificationCompat.MediaStyle;

public class VideoNotificationControl extends VideoNotificationChannel {
    private static final int REQUEST_CODE = 1201;
    private static final int FLAG = 0;
    private static final int ACTION_PREV = 0;
    private static final int ACTION_PLAY = 1;
    private static final int ACTION_NEXT = 3;
    private static final int NOTIFICATION_ID = 160;

    private PlayService mPlayService;
    private NotificationCompat.Builder mBuilder;

    public VideoNotificationControl(PlayService playService) {
        super(playService);
        mPlayService = playService;
    }

    private void createBuilder() {
        mBuilder =
                new NotificationCompat.Builder(mPlayService, NOTIFICATION_CHANNEL_ID).setSmallIcon(
                        R.drawable.ic_sun)
                        .setContentIntent(pendingIntentPlay())
                        .setChannelId(NOTIFICATION_CHANNEL_ID)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setStyle(new MediaStyle().setShowActionsInCompactView(ACTION_PREV,
                                ACTION_PLAY, ACTION_NEXT))
                        .addAction(R.drawable.ic_previous,
                                mPlayService.getString(R.string.btn_prev),
                                pendingIntentAction(Action.ACTION_PREVIOUS))
                        .addAction(mPlayService.isCheckPlaying() ? R.drawable.ic_play
                                        : R.drawable.ic_pause,
                                mPlayService.getString(R.string.btn_play),
                                pendingIntentAction(Action.ACTION_PLAY_OR_PAUSE))
                        .addAction(R.drawable.ic_stop, mPlayService.getString(R.string.btn_skip),
                                pendingIntentAction(Action.ACTION_STOP))
                        .addAction(R.drawable.ic_next, mPlayService.getString(R.string.fav_add),
                                pendingIntentAction(Action.ACTION_NEXT))
                        .setShowWhen(false)
                        .setVisibility(VISIBILITY_PUBLIC);
    }

    private PendingIntent pendingIntentPlay() {
        Intent playIntent = MainActivity.newInstance(mPlayService);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mPlayService);
        stackBuilder.addNextIntentWithParentStack(playIntent);
        return stackBuilder.getPendingIntent(1000, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent pendingIntentAction(String action) {
        Intent actionIntent = new Intent(mPlayService, PlayService.class);
        actionIntent.setAction(action);
        return PendingIntent.getService(mPlayService, REQUEST_CODE, actionIntent, FLAG);
    }

    public void updateNotification() {
        createBuilder();
        updateVideoInformation(mPlayService.getVideo(), mPlayService.getPosition());
    }

    private void updateVideoInformation(List<Video> videos, int position) {
        mBuilder.setContentTitle(videos.get(position).getTitle())
                .setContentText(videos.get(position).getDescription());
        handleForeground();
    }

    private void handleForeground() {
        mPlayService.startForeground(NOTIFICATION_ID, mBuilder.build());
    }
}

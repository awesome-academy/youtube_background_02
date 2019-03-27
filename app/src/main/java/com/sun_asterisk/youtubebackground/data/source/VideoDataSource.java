package com.sun_asterisk.youtubebackground.data.source;

import com.sun_asterisk.youtubebackground.data.model.Video;
import java.util.List;

public interface VideoDataSource {
    interface RemoteDataCallBack {
        void onSuccess(List<Video> videos);

        void onFail(Exception e);
    }

    interface LocalDataSource {
    }

    interface RemoteDataSource {
        void getVideosRemote(RemoteDataCallBack listener);
    }
}

package com.sun_asterisk.youtubebackground.data.source.remote;

import com.sun_asterisk.youtubebackground.data.source.VideoDataSource;
import com.sun_asterisk.youtubebackground.utils.Constant;

public class VideoRemoteDataSource implements VideoDataSource.RemoteDataSource {
    private static VideoRemoteDataSource sInstance;

    private VideoRemoteDataSource() {
    }

    public static VideoRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new VideoRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getVideosRemote(VideoDataSource.RemoteDataCallBack listener) {
        FetchData fetchData = new FetchData(listener);
        fetchData.execute(Constant.URL);
    }
}

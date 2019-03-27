package com.sun_asterisk.youtubebackground.data.source.repository;

import com.sun_asterisk.youtubebackground.data.source.VideoDataSource;

public class VideoRepository {
    private static VideoRepository sInstance;
    private VideoDataSource.RemoteDataSource mRemoteDataSource;
    private VideoDataSource.LocalDataSource mLocalDataSource;

    private VideoRepository(VideoDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static VideoRepository getInstance(VideoDataSource.RemoteDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new VideoRepository(remoteDataSource);
        }
        return sInstance;
    }

    public void getVideosRemote(VideoDataSource.RemoteDataCallBack listener) {
        mRemoteDataSource.getVideosRemote(listener);
    }
}

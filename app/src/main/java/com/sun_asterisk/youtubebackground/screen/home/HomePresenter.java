package com.sun_asterisk.youtubebackground.screen.home;

import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.data.source.VideoDataSource;
import com.sun_asterisk.youtubebackground.data.source.repository.VideoRepository;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private VideoRepository mVideoRepository;

    public HomePresenter(HomeContract.View view, VideoRepository videoRepository) {
        mView = view;
        mVideoRepository = videoRepository;
    }

    @Override
    public void getVideos() {
        mVideoRepository.getVideosRemote(new VideoDataSource.RemoteDataCallBack() {
            @Override
            public void onSuccess(List<Video> videos) {
                mView.onGetVideoSuccess(videos);
            }

            @Override
            public void onFail(Exception e) {
                mView.onError(e.getMessage());
            }
        });
    }
}

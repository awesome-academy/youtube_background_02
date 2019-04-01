package com.sun_asterisk.youtubebackground.screen.search;

import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.data.source.VideoDataSource;
import com.sun_asterisk.youtubebackground.data.source.repository.VideoRepository;
import java.util.List;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private VideoRepository mVideoRepository;

    public SearchPresenter(SearchContract.View view, VideoRepository videoRepository) {
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

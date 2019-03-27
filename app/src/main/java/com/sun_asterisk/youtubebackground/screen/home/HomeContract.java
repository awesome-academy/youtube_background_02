package com.sun_asterisk.youtubebackground.screen.home;

import com.sun_asterisk.youtubebackground.data.model.Video;
import java.util.List;

public interface HomeContract {
    interface View {
        void onGetVideoSuccess(List<Video> video);

        void onError(String s);
    }

    interface Presenter {
        void getVideos();
    }
}

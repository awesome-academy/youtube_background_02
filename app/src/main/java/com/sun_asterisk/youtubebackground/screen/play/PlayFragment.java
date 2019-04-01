package com.sun_asterisk.youtubebackground.screen.play;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.utils.Constant;
import java.util.ArrayList;
import java.util.List;

public class PlayFragment extends Fragment {
    private static final String EXTRA_POSITION = "EXTRA_POSITION";
    private static final String EXTRA_LIST = "EXTRA_LIST";
    private YouTubePlayer mYouTubePlayer;
    private int mPosition;
    private View mView;
    TextView mTextTitle, mTextDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.actionSearch);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static PlayFragment newInstance(int position, ArrayList<Video> videos) {
        PlayFragment fragment = new PlayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, position);
        bundle.putParcelableArrayList(EXTRA_LIST, videos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_play, container, false);
        initView();
        getData();
        return mView;
    }

    private void initView() {
        YouTubePlayerSupportFragment youTubePlayerSupportFragment =
                YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.youTubePlayerView, youTubePlayerSupportFragment).commit();
        mTextTitle = mView.findViewById(R.id.textTitlePlay);
        mTextDescription = mView.findViewById(R.id.textDescriptionPlay);
        youTubePlayerSupportFragment.initialize(Constant.KEY,
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

    private void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(EXTRA_POSITION);
            List<Video> videos = bundle.getParcelableArrayList(EXTRA_LIST);
            if (videos != null) {
                mTextTitle.setText(videos.get(mPosition).getTitle());
                mTextDescription.setText(videos.get(mPosition).getDescription());
            }
        }
    }
}

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
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.screen.main.MainActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayFragment extends Fragment {
    private static final String EXTRA_POSITION = "EXTRA_POSITION";
    private static final String EXTRA_LIST = "EXTRA_LIST";
    private YouTubePlayerSupportFragment mYouTubePlayerSupportFragment;
    private int mPosition;
    private View mView;
    private static PlayService mPlayService;
    TextView mTextTitle, mTextDescription;
    private List<Video> mVideos;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlayService = ((MainActivity) Objects.requireNonNull(getActivity())).getService();
        setViewYoutubePlayer();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        mPlayService.playVideo(mPosition, fragmentTransaction);
    }

    private void setViewYoutubePlayer() {
        mYouTubePlayerSupportFragment = mPlayService.getYouTubePlayerSupportFragment();
        if (mVideos != null) {
            mTextTitle.setText(mVideos.get(mPosition).getTitle());
            mTextDescription.setText(mVideos.get(mPosition).getDescription());
        }
    }

    private void initView() {
        mTextTitle = mView.findViewById(R.id.textTitlePlay);
        mTextDescription = mView.findViewById(R.id.textDescriptionPlay);
    }

    private void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(EXTRA_POSITION);
            mVideos = bundle.getParcelableArrayList(EXTRA_LIST);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

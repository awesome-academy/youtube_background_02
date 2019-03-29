package com.sun_asterisk.youtubebackground.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.data.source.remote.VideoRemoteDataSource;
import com.sun_asterisk.youtubebackground.data.source.repository.VideoRepository;
import com.sun_asterisk.youtubebackground.screen.play.OnItemClickListener;
import com.sun_asterisk.youtubebackground.screen.play.PlayFragment;
import com.sun_asterisk.youtubebackground.utils.Navigator;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View, OnItemClickListener {
    private HomeContract.Presenter mPresenter;
    private HomeAdapter mHomeAdapter;
    private VideoRepository mVideoRepository;
    private Navigator mNavigator;
    private ArrayList<Video> mVideos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mHomeAdapter = new HomeAdapter(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycleViewVideo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mHomeAdapter);
        mHomeAdapter.setOnItemClickListener(HomeFragment.this);
        mVideoRepository = VideoRepository.getInstance(VideoRemoteDataSource.getInstance());
        mPresenter = new HomePresenter(this, mVideoRepository);
        mPresenter.getVideos();
        mNavigator = new Navigator();
        return view;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onGetVideoSuccess(List<Video> video) {
        mVideos = new ArrayList<>();
        mVideos.addAll(video);
        mHomeAdapter.setData(video);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        mNavigator.addFragment(getActivity(), PlayFragment.newInstance(position, mVideos),
                R.layout.fragment_play);
    }
}

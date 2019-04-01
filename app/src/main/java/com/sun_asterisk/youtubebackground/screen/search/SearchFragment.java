package com.sun_asterisk.youtubebackground.screen.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.data.source.remote.VideoRemoteDataSource;
import com.sun_asterisk.youtubebackground.data.source.repository.VideoRepository;
import com.sun_asterisk.youtubebackground.screen.play.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment
        implements SearchContract.View, OnItemClickListener, SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener {
    private List<String> mTitles;
    private SearchAdapter mSearchAdapter;
    private List<String> mFilteredValues;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onGetVideoSuccess(List<Video> videos) {
        List<Video> mVideos = new ArrayList<>(videos);
        mTitles = new ArrayList<>();
        for (int i = 0; i < mVideos.size(); i++) {
            mTitles.add(mVideos.get(i).getTitle());
        }
        mSearchAdapter.setData(mTitles);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        VideoRepository videoRepository =
                VideoRepository.getInstance(VideoRemoteDataSource.getInstance());
        SearchContract.Presenter presenter = new SearchPresenter(this, videoRepository);
        mFilteredValues = new ArrayList<>();
        presenter.getVideos();
    }

    private void initView(View view) {
        mSearchAdapter = new SearchAdapter(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycleViewSearchVideo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.setOnItemClickListener(SearchFragment.this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_fragment_search, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        } else {
            mFilteredValues.clear();
            mFilteredValues.addAll(mTitles);
            for (String value : mTitles) {

                if (!value.toLowerCase().contains(newText.toLowerCase())) {
                    mFilteredValues.remove(value);
                }
            }
            mSearchAdapter.setData(mFilteredValues);
        }
        return false;
    }

    private void resetSearch() {
        mSearchAdapter.setData(mTitles);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }
}

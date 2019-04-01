package com.sun_asterisk.youtubebackground.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.sun_asterisk.youtubebackground.R;

public class Navigator {
    private static final String HOME = "HOME";
    private static final String PLAY = "PLAY";
    private static final String SEARCH = "SEARCH";

    public void addFragment(FragmentActivity fragmentActivity, Fragment fragment, int idLayout) {
        switch (idLayout) {
            case R.layout.fragment_home:
                addFragmentToBackStack(fragmentActivity, fragment, HOME);
                break;
            case R.layout.fragment_play:
                addFragmentToBackStack(fragmentActivity, fragment, PLAY);
                break;
            case R.layout.fragment_search:
                addFragmentToBackStack(fragmentActivity, fragment, SEARCH);
                break;
        }
    }
    private void addFragmentToBackStack(FragmentActivity fragmentActivity, Fragment fragment,
            String tag){
        FragmentTransaction fragmentTransaction =
                fragmentActivity.getSupportFragmentManager().beginTransaction();
        if (fragmentActivity.getSupportFragmentManager()
                .findFragmentByTag(fragment.getClass().getSimpleName()) == null) {
            fragmentTransaction.add(R.id.frameContent, fragment,
                    fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            fragmentActivity.getSupportFragmentManager().popBackStack(tag, 0);
        }
    }
}

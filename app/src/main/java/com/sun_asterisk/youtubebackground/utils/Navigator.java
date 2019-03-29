package com.sun_asterisk.youtubebackground.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.sun_asterisk.youtubebackground.R;

public class Navigator {
    private static final String HOME = "HOME";
    private static final String PLAY = "PLAY";

    public void addFragment(FragmentActivity fragmentActivity, Fragment fragment, int idLayout) {
        switch (idLayout) {
            case R.layout.fragment_home:
                FragmentTransaction fragmentTransaction =
                        fragmentActivity.getSupportFragmentManager().beginTransaction();
                if (fragmentActivity.getSupportFragmentManager()
                        .findFragmentByTag(fragment.getClass().getSimpleName()) == null) {
                    fragmentTransaction.add(R.id.frameContent, fragment,
                            fragment.getClass().getSimpleName());
                    fragmentTransaction.addToBackStack(HOME);
                    fragmentTransaction.commit();
                } else {
                    fragmentActivity.getSupportFragmentManager().popBackStack(HOME, 0);
                }
                break;
            case R.layout.fragment_play:
                FragmentTransaction fragmentTransactionPlay =
                        fragmentActivity.getSupportFragmentManager().beginTransaction();
                if (fragmentActivity.getSupportFragmentManager()
                        .findFragmentByTag(fragment.getClass().getSimpleName()) == null) {
                    fragmentTransactionPlay.add(R.id.frameContent, fragment,
                            fragment.getClass().getSimpleName());
                    fragmentTransactionPlay.addToBackStack(PLAY);
                    fragmentTransactionPlay.commit();
                } else {
                    fragmentActivity.getSupportFragmentManager().popBackStack(PLAY, 0);
                }
                break;
        }
    }
}

package com.sun_asterisk.youtubebackground.screen.main;

import android.support.v4.app.Fragment;

public interface MainListener {
    void addFragment(Fragment fragment, int idLayout, boolean addToBackStack);
}

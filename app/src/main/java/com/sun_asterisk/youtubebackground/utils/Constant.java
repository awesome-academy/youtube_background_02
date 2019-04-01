package com.sun_asterisk.youtubebackground.utils;

import com.sun_asterisk.youtubebackground.BuildConfig;

public class Constant {
    public static final String ITEM = "items";
    public static final String SNIPPET = "snippet";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String THUMBNAIL = "thumbnails";
    public static final String SIZE = "standard";
    public static final String URL = "url";
    public static final String SOURCE = "resourceId";
    public static final String ID = "videoId";
    private static final String API =
            "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=";
    public static final String KEY = BuildConfig.API_KEY;
    public static final String PLAYLIST = BuildConfig.PLAYLIST;
    private static final String MAX_RESULT = "&maxResults=20";
    public static final String URL_API = API + PLAYLIST + "&key=" + KEY + MAX_RESULT;
}

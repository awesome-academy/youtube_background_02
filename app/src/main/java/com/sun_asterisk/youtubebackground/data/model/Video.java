package com.sun_asterisk.youtubebackground.data.model;

public class Video {
    private String mId;
    private String mTitle;
    private String mThumbnail;
    private String mDescription;

    public Video() {
    }

    public Video(VideoBuilder videoBuilder) {
        mId = videoBuilder.mId;
        mTitle = videoBuilder.mTitle;
        mThumbnail = videoBuilder.mThumbnail;
        mDescription = videoBuilder.mDescription;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public static class VideoBuilder {
        private String mId;
        private String mTitle;
        private String mThumbnail;
        private String mDescription;

        public VideoBuilder() {
        }

        public VideoBuilder(String id, String title, String thumbnail, String description) {
            mId = id;
            mTitle = title;
            mThumbnail = thumbnail;
            mDescription = description;
        }

        public VideoBuilder setId(String id) {
            mId = id;
            return this;
        }

        public VideoBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public VideoBuilder setThumbnail(String thumbnail) {
            mThumbnail = thumbnail;
            return this;
        }

        public VideoBuilder setDescription(String description) {
            mDescription = description;
            return this;
        }

        public Video build() {
            return new Video(this);
        }
    }

    public final class VideoEntry {
        public static final String ITEM = "items";
        public static final String TITLE = "title";
        public static final String SOURCE = "resourceId";
        public static final String ID = "videoId";
        public static final String DESCRIPTION = "description";
        public static final String THUMBNAIL = "thumbnails";
    }
}

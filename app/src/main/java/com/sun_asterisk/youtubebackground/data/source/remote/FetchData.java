package com.sun_asterisk.youtubebackground.data.source.remote;

import android.os.AsyncTask;
import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.data.source.VideoDataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sun_asterisk.youtubebackground.utils.Constant.ITEM;
import static com.sun_asterisk.youtubebackground.utils.Constant.SNIPPET;
import static com.sun_asterisk.youtubebackground.utils.Constant.SOURCE;

public class FetchData extends AsyncTask<String, Void, List<Video>> {
    private VideoDataSource.RemoteDataCallBack mRemoteDataCallBack;
    private List<Video> mVideos = new ArrayList<>();
    private String mData = "";

    public FetchData(VideoDataSource.RemoteDataCallBack dataCallBack) {
        mRemoteDataCallBack = dataCallBack;
    }

    @Override
    protected List<Video> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                mData = mData + line;
            }
            bufferedReader.close();
            mVideos = parseJson(mData);
        } catch (Exception e) {
            mRemoteDataCallBack.onFail(e);
        }
        return mVideos;
    }

    @Override
    protected void onPostExecute(List<Video> videos) {
        super.onPostExecute(videos);
        mRemoteDataCallBack.onSuccess(videos);
    }

    private List<Video> parseJson(String data) throws JSONException {
        List<Video> videos = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray(ITEM);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i).getJSONObject(SNIPPET);
            Video video = new Video.VideoBuilder().setId(
                    object.getJSONObject(SOURCE).getString(Video.VideoEntry.ID))
                    .setTitle(object.getString(Video.VideoEntry.TITLE))
                    .setDescription(object.getString(Video.VideoEntry.DESCRIPTION))
                    .setThumbnail(object.getString(Video.VideoEntry.THUMBNAIL))
                    .build();
            videos.add(video);
        }
        return videos;
    }
}

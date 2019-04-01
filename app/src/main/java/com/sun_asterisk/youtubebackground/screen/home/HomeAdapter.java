package com.sun_asterisk.youtubebackground.screen.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.data.model.Video;
import com.sun_asterisk.youtubebackground.screen.play.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context mContext;
    private List<Video> mVideos;
    private OnItemClickListener mOnItemClickListener;

    void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    HomeAdapter(Context context) {
        mContext = context;
        mVideos = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.video_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        viewHolder.bindView(mVideos.get(position));
    }

    public void setData(List<Video> videos) {
        if (videos != null) {
            mVideos.clear();
            mVideos.addAll(videos);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mVideos != null ? mVideos.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageVideo;
        TextView mTextTitle;
        TextView mTextDescription;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageVideo = itemView.findViewById(R.id.imageVideo);
            mTextTitle = itemView.findViewById(R.id.textTitle);
            mTextDescription = itemView.findViewById(R.id.textDescription);
            itemView.setOnClickListener(this);
        }

        void bindView(Video video) {
            Glide.with(mContext).load(video.getThumbnail()).into(mImageVideo);
            mTextTitle.setText(video.getTitle());
            mTextDescription.setText(video.getDescription());
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}

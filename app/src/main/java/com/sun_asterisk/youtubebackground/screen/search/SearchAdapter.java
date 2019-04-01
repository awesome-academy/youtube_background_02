package com.sun_asterisk.youtubebackground.screen.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.sun_asterisk.youtubebackground.R;
import com.sun_asterisk.youtubebackground.screen.play.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mTitles;
    private OnItemClickListener mOnItemClickListener;

    void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    SearchAdapter(Context context) {
        mContext = context;
        mTitles = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.search_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        viewHolder.bindView(mTitles.get(position));
    }

    public void setData(List<String> videos) {
        if (videos != null) {
            mTitles.clear();
            mTitles.addAll(videos);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mTitles != null ? mTitles.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextTitleVideo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextTitleVideo = itemView.findViewById(R.id.textTitleVideo);
            itemView.setOnClickListener(this);
        }

        void bindView(String video) {
            mTextTitleVideo.setText(video);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
                Toast.makeText(mContext, mTitles.get(getAdapterPosition()), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}

package com.example.workoutapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workoutapp.R;
import com.example.workoutapp.base.ItemClickListener;
import com.example.workoutapp.databinding.ItemVideoLayoutBinding;
import com.example.workoutapp.model.VideoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {
    private ArrayList<VideoModel> videos;
    private ItemClickListener<VideoModel> onClickListener;
    private Context context;

    public VideosAdapter(Context context, ArrayList<VideoModel> videos, ItemClickListener<VideoModel> onClickListener) {
        this.videos = videos;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideoLayoutBinding binding = ItemVideoLayoutBinding
                .inflate(LayoutInflater.from(context), parent, false);

        return new VideosViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder holder, int position) {
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class VideosViewHolder extends RecyclerView.ViewHolder {

        ImageView ivVideoThumbnail;
        TextView tvVideoTitle;
        TextView tvVideoStatus;
        LinearLayout linearLayout;

        public VideosViewHolder(@NonNull ItemVideoLayoutBinding binding) {
            super(binding.getRoot());

            ivVideoThumbnail = binding.ivVideoThumbnail;
            tvVideoTitle = binding.tvVideoTitle;
            tvVideoStatus = binding.tvVideoStatus;
            linearLayout = binding.linearLayout;
        }

        void bind(final VideoModel model) {
            tvVideoTitle.setText(model.getTitle());

            Picasso.get().load(model.getThumbnail())
                    .placeholder(itemView.getResources().getDrawable(R.drawable.image_placeholder))
                    .into(ivVideoThumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClicked(model);
                }
            });
        }
    }
}

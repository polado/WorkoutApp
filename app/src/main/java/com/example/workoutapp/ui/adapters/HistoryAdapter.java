package com.example.workoutapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workoutapp.R;
import com.example.workoutapp.base.ItemClickListener;
import com.example.workoutapp.databinding.ItemHistoryLayoutBinding;
import com.example.workoutapp.model.HistoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VideosViewHolder> {
    private ArrayList<HistoryModel> videos;
    private ItemClickListener<HistoryModel> onClickListener;
    private Context context;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> videos, ItemClickListener<HistoryModel> onClickListener) {
        this.videos = videos;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryLayoutBinding binding = ItemHistoryLayoutBinding
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
        ImageView ivIcon;
        TextView tvCategoryName;

        public VideosViewHolder(@NonNull ItemHistoryLayoutBinding binding) {
            super(binding.getRoot());

            ivVideoThumbnail = binding.ivVideoThumbnail;
            tvVideoTitle = binding.tvVideoTitle;
            ivIcon = binding.ivIcon;
            tvCategoryName = binding.tvCategoryName;
        }

        void bind(final HistoryModel model) {

            System.out.println(model.getCategoryID());
            System.out.println(model.getCategoryName());
            System.out.println(model.getCategoryIcon());

            tvVideoTitle.setText(model.getVideo().getTitle());

            Picasso.get().load(model.getVideo().getThumbnail())
                    .placeholder(itemView.getResources().getDrawable(R.drawable.image_placeholder))
                    .into(ivVideoThumbnail);

            Picasso.get().load(model.getCategoryIcon()).into(ivIcon);

            tvCategoryName.setText(model.getCategoryName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClicked(model);
                }
            });
        }
    }
}

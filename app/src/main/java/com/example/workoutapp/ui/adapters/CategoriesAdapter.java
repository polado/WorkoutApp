package com.example.workoutapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workoutapp.base.ItemClickListener;
import com.example.workoutapp.databinding.ItemCategoryLayoutBinding;
import com.example.workoutapp.model.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private ArrayList<CategoryModel> categories;
    private ItemClickListener<CategoryModel> onClickListener;

    public CategoriesAdapter(ArrayList<CategoryModel> categories, ItemClickListener<CategoryModel> onClickListener) {
        this.categories = categories;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryLayoutBinding binding = ItemCategoryLayoutBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new CategoriesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvCategoryName;

        public CategoriesViewHolder(@NonNull ItemCategoryLayoutBinding binding) {
            super(binding.getRoot());

            ivIcon = binding.ivIcon;
            tvCategoryName = binding.tvCategoryName;
        }

        void bind(final CategoryModel model) {
            Picasso.get().load(model.getIcon()).into(ivIcon);

            tvCategoryName.setText(model.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClicked(model);
                }
            });
        }
    }
}

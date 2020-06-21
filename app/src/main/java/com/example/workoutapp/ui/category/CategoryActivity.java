package com.example.workoutapp.ui.category;

import android.content.Context;
import android.content.Intent;

import com.example.workoutapp.R;
import com.example.workoutapp.base.BaseActivity;
import com.example.workoutapp.base.ItemClickListener;
import com.example.workoutapp.databinding.ActivityCategoryBinding;
import com.example.workoutapp.model.CategoryModel;
import com.example.workoutapp.model.VideoModel;
import com.example.workoutapp.ui.adapters.VideosAdapter;
import com.example.workoutapp.ui.video.VideoActivity;

import java.util.ArrayList;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CategoryActivity extends BaseActivity implements CategoryView {
    ActivityCategoryBinding binding;
    private CategoryPresenter presenter;

    public static void startCategoryActivity(Context context, CategoryModel model) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra("CATEGORY", model);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_category;
    }

    @Override
    protected void onViewCreated() {
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitleWithBack("Category");

        presenter = new CategoryPresenter(this);

        final CategoryModel category = (CategoryModel) getIntent().getSerializableExtra("CATEGORY");
        presenter.getVideos(category);

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getVideos(category);
                binding.swipeToRefresh.setRefreshing(false);
            }
        });
    }


    @Override
    public void onReceiveData(ArrayList<VideoModel> videos) {
        hideLoading();

        VideosAdapter adapter = new VideosAdapter(this, videos, new ItemClickListener<VideoModel>() {
            @Override
            public void onItemClicked(VideoModel model) {
                VideoActivity.startVideoActivity(CategoryActivity.this, model);
            }
        });
        binding.rvVideos.setAdapter(adapter);
    }
}

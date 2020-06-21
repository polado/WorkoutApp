package com.example.workoutapp.ui.history;

import com.example.workoutapp.R;
import com.example.workoutapp.base.BaseActivity;
import com.example.workoutapp.base.ItemClickListener;
import com.example.workoutapp.databinding.ActivityHistoryBinding;
import com.example.workoutapp.model.HistoryModel;
import com.example.workoutapp.model.VideoModel;
import com.example.workoutapp.ui.adapters.HistoryAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends BaseActivity implements HistoryView {
    ActivityHistoryBinding binding;
    ArrayList<HistoryModel> historyList;
    private HistoryPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_history;
    }

    @Override
    protected void onViewCreated() {
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitleWithBack("History");

        presenter = new HistoryPresenter(this);
        presenter.getHistory();
    }

    @Override
    public void onFail(boolean result) {
        hideLoading();
    }

    @Override
    public void onReceiveList(final ArrayList<HistoryModel> historyList) {
        for (HistoryModel model : historyList) {
            if (model.getVideo() == null)
                return;
        }
        hideLoading();

        TabLayout tabLayout = binding.tlStats;

        final ArrayList<String> dates = HistoryModel.getDates(historyList);
        Collections.reverse(dates);

        for (String date : dates) {
            tabLayout.addTab(tabLayout.newTab().setText(date));
        }

        if (!historyList.isEmpty())
            this.historyList = VideoModel.filterByDate(historyList, dates.get(0));
        else
            this.historyList = new ArrayList<>();

        final HistoryAdapter adapter = new HistoryAdapter(this, this.historyList, new ItemClickListener<HistoryModel>() {
            @Override
            public void onItemClicked(HistoryModel model) {
            }
        });
        binding.rvVideos.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getText().toString() + VideoModel.filterByDate(historyList, tab.getText().toString()).size());
                HistoryActivity.this.historyList.clear();
                HistoryActivity.this.historyList.addAll(VideoModel.filterByDate(historyList, tab.getText().toString()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

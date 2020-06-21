package com.example.workoutapp.ui.home;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.workoutapp.R;
import com.example.workoutapp.base.BaseActivity;
import com.example.workoutapp.base.ItemClickListener;
import com.example.workoutapp.databinding.ActivityHomeBinding;
import com.example.workoutapp.model.CategoryModel;
import com.example.workoutapp.ui.adapters.CategoriesAdapter;
import com.example.workoutapp.ui.category.CategoryActivity;
import com.example.workoutapp.ui.login.LoginActivity;
import com.example.workoutapp.ui.history.HistoryActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeActivity extends BaseActivity implements HomeView {
    ActivityHomeBinding binding;
    private HomePresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void onViewCreated() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Home");

        presenter = new HomePresenter(this);

        presenter.getCategories();

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCategories();
                binding.swipeToRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_stats:
                startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
                return true;
            case R.id.mi_sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onReceiveData(ArrayList<CategoryModel> categories) {
        hideLoading();

        CategoriesAdapter adapter = new CategoriesAdapter(categories, new ItemClickListener<CategoryModel>() {
            @Override
            public void onItemClicked(CategoryModel model) {
                CategoryActivity.startCategoryActivity(HomeActivity.this, model);
            }
        });
        binding.rvCategories.setAdapter(adapter);
    }
}

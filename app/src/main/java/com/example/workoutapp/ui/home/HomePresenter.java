package com.example.workoutapp.ui.home;

import com.example.workoutapp.base.BasePresenter;
import com.example.workoutapp.base.BaseView;
import com.example.workoutapp.model.CategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;

interface HomeView extends BaseView {
    void onReceiveData(ArrayList<CategoryModel> categories);
}

public class HomePresenter extends BasePresenter {
    private HomeView view;
    private FirebaseFirestore firestore;

    public HomePresenter(HomeView view) {
        super(view);
        this.view = view;
        firestore = FirebaseFirestore.getInstance();
    }

    void getCategories() {
        view.showLoading();
        firestore.collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<CategoryModel> categories = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        CategoryModel model = snapshot.toObject(CategoryModel.class);
                        model.setId(snapshot.getId());
                        categories.add(model);
                    }
                    view.onReceiveData(categories);
                } else {
                    view.hideLoading();
                    view.showErrorMsg("Something Went Wrong");
                }
            }
        });
    }
}

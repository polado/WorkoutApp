package com.example.workoutapp.ui.category;

import com.example.workoutapp.base.BasePresenter;
import com.example.workoutapp.base.BaseView;
import com.example.workoutapp.model.CategoryModel;
import com.example.workoutapp.model.VideoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;

interface CategoryView extends BaseView {
    void onReceiveData(ArrayList<VideoModel> videos);
}

public class CategoryPresenter extends BasePresenter {
    private CategoryView view;
    private FirebaseFirestore firestore;

    public CategoryPresenter(CategoryView view) {
        super(view);
        this.view = view;
        firestore = FirebaseFirestore.getInstance();
    }

    void getVideos(final CategoryModel category) {
        view.showLoading();
        firestore.collection("categories").document(category.getId())
                .collection("videos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<VideoModel> videos = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        VideoModel model = snapshot.toObject(VideoModel.class);
                        model.setId(snapshot.getId());
                        model.setCategoryModel(category);
                        videos.add(model);
                    }
                    view.onReceiveData(videos);
                } else {
                    view.hideLoading();
                    view.showErrorMsg("Something Went Wrong");
                }
            }
        });
    }
}

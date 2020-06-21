package com.example.workoutapp.ui.video;

import com.example.workoutapp.base.BasePresenter;
import com.example.workoutapp.base.BaseView;
import com.example.workoutapp.model.VideoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;

interface VideoView extends BaseView {
    void onReceiveData(boolean result);
}

public class VideoPresenter extends BasePresenter {
    private VideoView view;
    private FirebaseFirestore firestore;

    public VideoPresenter(VideoView view) {
        super(view);
        this.view = view;
        firestore = FirebaseFirestore.getInstance();
    }

    void isWatched(final VideoModel model) {
        view.showLoading();

        final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String id = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        firestore.collection("users")
                .document(id)
                .collection("history")
                .document(date + "-" + model.getId())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        view.onReceiveData(true);
                    } else {
                        setWatched(model, date);
                    }
                } else {
                    setWatched(model, date);
                }
            }
        });
    }

    private void setWatched(VideoModel model, String date) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("date", date);
        userMap.put("reference", firestore.document(
                "categories/" + model.getCategoryModel().getId()
                        + "/videos/" + model.getId()));
        userMap.putAll(model.getCategoryModel().getMap());

        String id = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        firestore.collection("users")
                .document(id)
                .collection("history")
                .document(date + "-" + model.getId())
                .set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        view.onReceiveData(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.onReceiveData(false);
                    }
                });
    }
}

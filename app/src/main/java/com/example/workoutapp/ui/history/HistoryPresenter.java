package com.example.workoutapp.ui.history;

import com.example.workoutapp.base.BasePresenter;
import com.example.workoutapp.base.BaseView;
import com.example.workoutapp.model.HistoryModel;
import com.example.workoutapp.model.VideoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;

interface HistoryView extends BaseView {
    void onFail(boolean result);

    void onReceiveList(ArrayList<HistoryModel> historyList);
}

public class HistoryPresenter extends BasePresenter {
    private HistoryView view;
    private FirebaseFirestore firestore;

    public HistoryPresenter(HistoryView view) {
        super(view);
        this.view = view;
        firestore = FirebaseFirestore.getInstance();
    }

    void getHistory() {
        view.showLoading();

        String id = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        firestore.collection("users")
                .document(id)
                .collection("history")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    final ArrayList<HistoryModel> historyList = new ArrayList<>();
                    if (task.getResult().getDocuments().isEmpty())
                        view.onReceiveList(historyList);
                    else
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            final HistoryModel model = snapshot.toObject(HistoryModel.class);
                            model.setId(snapshot.getId());
                            historyList.add(model);
                            model.getReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        VideoModel video = task.getResult().toObject(VideoModel.class);
                                        video.setId(task.getResult().getId());
                                        model.setVideo(video);
                                        view.onReceiveList(historyList);
                                    } else {
                                        view.onFail(false);
                                    }
                                }
                            });
                        }
                } else {
                    view.onFail(false);
                }
            }
        });
    }
}

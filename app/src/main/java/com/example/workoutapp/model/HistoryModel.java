package com.example.workoutapp.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class HistoryModel {
    private String id;
    private String date;
    private DocumentReference reference;
    private VideoModel video;
    private String categoryID;
    private String categoryIcon;
    private String categoryName;

    public HistoryModel() {
    }

    public static ArrayList<String> getDates(ArrayList<HistoryModel> historyList) {
        ArrayList<String> dates = new ArrayList<>();
        for (HistoryModel model : historyList) {
            if (!dates.contains(model.date))
                dates.add(model.date);
        }
        return dates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DocumentReference getReference() {
        return reference;
    }

    public void setReference(DocumentReference reference) {
        this.reference = reference;
    }

    public VideoModel getVideo() {
        return video;
    }

    public void setVideo(VideoModel video) {
        this.video = video;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

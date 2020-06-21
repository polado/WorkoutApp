package com.example.workoutapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoModel implements Serializable {
    private String id;
    private String title;
    private String url;
    private String thumbnail;
    private boolean watched;
    private CategoryModel categoryModel;

    public VideoModel() {
    }

    public static ArrayList<HistoryModel> filterByDate(ArrayList<HistoryModel> historyList, String date) {
        ArrayList<HistoryModel> list = new ArrayList<>();
        for (HistoryModel model : historyList) {
            if (model.getDate().equals(date))
                list.add(model);
        }
        return list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }
}

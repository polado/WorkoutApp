package com.example.workoutapp.base;

public interface BaseView {
    void showLoading();

    void showLoading(String msg);

    void hideLoading();

    void showSuccessMsg(String msg);

    void showErrorMsg(String msg);

}
